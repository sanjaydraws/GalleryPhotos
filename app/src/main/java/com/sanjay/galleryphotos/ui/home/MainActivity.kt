package com.sanjay.galleryphotos.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.constants.PERMISSION_READ_STORAGE
import com.sanjay.galleryphotos.constants.PERMISSION_WRITE_STORAGE
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.extension.getPermissionString
import com.sanjay.galleryphotos.models.Directory
import com.sanjay.galleryphotos.models.Model_images
import com.sanjay.galleryphotos.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var binding:ActivityMainBinding? = null
    private val TAG = "MainActivity"
    private val mDirectoryAdapter by lazy{
        DirectoriesAdapter(ArrayList())
    }
    private var boolean_folder = false

    private val REQUEST_PERMISSIONS = 100
    private var al_images: ArrayList<Model_images> = ArrayList()

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
        val directoriesList = ArrayList<Directory>()
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://wallsdesk.com/wp-content/uploads/2016/11/Pictures-of-Chicago-.jpg"))
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://www.pixelstalk.net/wp-content/uploads/2016/07/Wallpapers-pexels-photo.jpg"))
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://wallpapersdsc.net/wp-content/uploads/2016/09/Charleston-Images.jpg"))
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://jooinn.com/images/dramatic-landscape-7.jpg"))
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://tse3.mm.bing.net/th?id=OIP.0iqvqUM-_MntTZp4CMBaigHaEK&pid=Api&P=0&w=321&h=182"))
        directoriesList.add(Directory(id = 0, dirName = "Camera", "https://tse3.mm.bing.net/th?id=OIP.lqtsWbAaz2UVlJShP10hywHaE8&pid=Api&P=0&w=259&h=173"))
        mDirectoryAdapter.updateData(directoriesList = directoriesList)

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            &&
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(getPermissionString(PERMISSION_READ_STORAGE),
                getPermissionString(PERMISSION_WRITE_STORAGE)), REQUEST_PERMISSIONS);
        } else {
            Log.e("Else", "Else")
            val arr = fn_imagespath()
            Log.d(TAG, "initArguments: ArrrayImages  $arr")
        }


    }

    override fun initViews() {

    }

    override fun setupListener() {
    }

    override fun initObservers() {
    }

    override fun loadData() {
    }


    fun fn_imagespath(): ArrayList<Model_images> {
        al_images.clear()
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
            for (i in 0 until al_images.size) {
                if (al_images[i].str_folder
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
                al_path.addAll(al_images.get(int_position).getAl_imagepath())
                al_path.add(absolutePathOfImage)
                al_images.get(int_position).setAl_imagepath(al_path)
            } else {
                val al_path: ArrayList<String?> = ArrayList()
                al_path.add(absolutePathOfImage)
                val obj_model = Model_images()
                obj_model.str_folder = column_index_folder_name?.let { cursor.getString(it) }
                obj_model.setAl_imagepath(al_path)
                al_images.add(obj_model)
            }
        }
        for (i in 0 until al_images.size) {
            Log.e("FOLDER", al_images.get(i).getStr_folder())
            for (j in 0 until al_images[i].al_imagepath.size) {
                Log.e("FILE", al_images[i].al_imagepath[j])
            }
        }

        return al_images
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
                        fn_imagespath()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    i++
                }
            }
        }

    }

}