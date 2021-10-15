package com.sanjay.galleryphotos.ui.home

import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.constants.PERMISSION_READ_STORAGE
import com.sanjay.galleryphotos.constants.PERMISSION_WRITE_STORAGE
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.extension.getPermissionString
import com.sanjay.galleryphotos.extension.hasPermission
import com.sanjay.galleryphotos.models.ModelDirectory
import com.sanjay.galleryphotos.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var binding:ActivityMainBinding? = null
    private val TAG = "MainActivity"
    private val mDirectoryAdapter by lazy{
        DirectoriesAdapter(ArrayList()){

        }
    }
    private var boolean_folder = false

    private val REQUEST_PERMISSIONS = 100
    private var directoriesList: ArrayList<ModelDirectory> = ArrayList()

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
    private fun getDirectories(): ArrayList<ModelDirectory> {
        directoriesList.clear()
        var int_position = 0
        val cursor: Cursor?
        var absolutePathOfImage: String? = null
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        cursor = applicationContext.contentResolver.query(
            uri, projection, null, null,
            "$orderBy DESC"
        )
        val column_index_data: Int? = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val column_index_folder_name: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor?.moveToNext() == true) {
            absolutePathOfImage = column_index_data?.let { cursor.getString(it) }
            absolutePathOfImage?.let { Log.e("Column", it) }
            column_index_folder_name?.let { cursor.getString(it) }?.let { Log.e("Folder", it) }
            for (i in 0 until directoriesList.size) {
                if (directoriesList[i].str_folder
                        .equals(column_index_folder_name?.let { cursor.getString(it) })
                ) {
                    boolean_folder = true
                    int_position = i
                    break
                } else {
                    boolean_folder = false
                }
            }
            if (boolean_folder) {
                val al_path: ArrayList<String?> = ArrayList()
                al_path.addAll(directoriesList.get(int_position).getAl_imagepath())
                al_path.add(absolutePathOfImage)
                directoriesList.get(int_position).setAl_imagepath(al_path)
            } else {
                val al_path: ArrayList<String?> = ArrayList()
                al_path.add(absolutePathOfImage)
                val obj_model = ModelDirectory()
                obj_model.str_folder = column_index_folder_name?.let { cursor.getString(it) }
                obj_model.setAl_imagepath(al_path)
                directoriesList.add(obj_model)
            }
        }
        for (i in 0 until directoriesList.size) {
            Log.e("FOLDER", directoriesList.get(i).getStr_folder())
            for (j in 0 until directoriesList[i].al_imagepath.size) {
                Log.e("FILE", directoriesList[i].al_imagepath[j])
            }
        }

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