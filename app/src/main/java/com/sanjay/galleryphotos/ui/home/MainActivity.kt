package com.sanjay.galleryphotos.ui.home

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.constants.BY_MEDIA_DATE_TAKEN
import com.sanjay.galleryphotos.constants.PERMISSION_READ_STORAGE
import com.sanjay.galleryphotos.constants.PERMISSION_WRITE_STORAGE
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.extension.getPermissionString
import com.sanjay.galleryphotos.extension.hasPermission
import com.sanjay.galleryphotos.models.Directory
import com.sanjay.galleryphotos.ui.base.BaseActivity
import com.sanjay.galleryphotos.ui.photos.DirectoryPhotosActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var binding:ActivityMainBinding? = null
    private val TAG = "MainActivity"
    private val mDirectoryAdapter by lazy{
        DirectoriesAdapter(ArrayList()){
            DirectoryPhotosActivity.startIntent(this@MainActivity, it)
        }
    }
    private var boolean_folder = false

    private val REQUEST_PERMISSIONS = 100
    private var directoriesList: ArrayList<Directory> = ArrayList()

    @Inject
    lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }

        init()
    }

    override fun initArguments() {
        binding?.directoriesGrid?.adapter = mDirectoryAdapter
        askPermission()
    }

    override fun initViews() {

    }

    override fun setupListener() {
    }

    override fun initObservers() {
    }

    override fun loadData() {
    }


    private fun askPermission(){
        if ( !hasPermission(PERMISSION_WRITE_STORAGE) && !hasPermission(PERMISSION_READ_STORAGE)
        ) {
            // ask permission if permission denied
            ActivityCompat.requestPermissions(this, arrayOf(getPermissionString(PERMISSION_READ_STORAGE),
                getPermissionString(PERMISSION_WRITE_STORAGE)), REQUEST_PERMISSIONS);
        } else {
            Log.e("Else", "Else")
            val dir = getDirectories()
            mDirectoryAdapter.updateData(directoriesList = dir)
            Log.d(TAG, "initArguments: ArrrayImages  $dir")
        }
    }
    private fun getDirectories(): ArrayList<Directory> {
        directoriesList.clear()
        var int_position = 0
        var absolutePathOfImage: String? = null
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor: Cursor? = applicationContext.contentResolver.query(uri, projection, null, null, "$BY_MEDIA_DATE_TAKEN DESC")
        val columnIndexData: Int? = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA) // 0
        val columnIndexDirectoryName: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME) // 1
        while (cursor?.moveToNext() == true) {
            absolutePathOfImage = columnIndexData?.let { cursor.getString(it) } // storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20211018-WA0001.jpg
            columnIndexDirectoryName?.let { cursor.getString(it) }?.let { Log.e("Folder", it) }
            for (i in 0 until directoriesList.size) {
                if (directoriesList[i].directoryName.equals(columnIndexDirectoryName?.let { cursor.getString(it) })) {
                    // if directory already present in list
                    boolean_folder = true
                    int_position = i
                    break
                } else {
                    // if size is zero or directory not present in list
                    boolean_folder = false
                }
            }
            if (boolean_folder) {
                // when directory already present
                val photosPath: ArrayList<String>? = ArrayList()
                directoriesList[int_position].allPhotos?.let { photosPath?.addAll(it) } // added to new array
                absolutePathOfImage?.let { photosPath?.add(it) } // added new path
                directoriesList[int_position].allPhotos = photosPath // copy on that index
            } else {
                //if size is zero or directory not present in list and  added new directory in list
                val photosPath: ArrayList<String>? = ArrayList()
                absolutePathOfImage?.let { photosPath?.add(it) }
                val directory = Directory()
                directory.directoryName = columnIndexDirectoryName?.let { cursor.getString(it) }
                directory.allPhotos = photosPath
                directoriesList.add(directory)
            }
        }
//        for (i in 0 until directoriesList.size) {
//            directoriesList[i].directoryName?.let { Log.e("FOLDER", it) }
//            for (j in 0 until directoriesList[i].allPhotos?.size!!) {
//                directoriesList[i].allPhotos?.get(j)?.let { Log.e("FILE", it) }
//            }
//        }
        return directoriesList
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSIONS -> {
                var i = 0
                while (i < grantResults.size) {
                    if (grantResults.isNotEmpty() && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mDirectoryAdapter.updateData(getDirectories())
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.The_app_was_not_allowed_to_read_or_write_to_your_storage),
                            Toast.LENGTH_LONG
                        ).show()
                        finish() // if permission denied
                        return
                    }
                    i++
                }
            }
        }

    }

}