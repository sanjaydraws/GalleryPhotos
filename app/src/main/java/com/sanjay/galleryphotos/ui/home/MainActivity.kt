package com.sanjay.galleryphotos.ui.home

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.constants.BY_MEDIA_DATE_TAKEN
import com.sanjay.galleryphotos.constants.PERMISSION_READ_STORAGE
import com.sanjay.galleryphotos.constants.PERMISSION_WRITE_STORAGE
import com.sanjay.galleryphotos.constants.REQUEST_PERMISSIONS
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.extension.getPermissionString
import com.sanjay.galleryphotos.extension.hasPermission
import com.sanjay.galleryphotos.models.Directory
import com.sanjay.galleryphotos.repository.DirectoryRepositoryImpl
import com.sanjay.galleryphotos.ui.base.BaseActivity
import com.sanjay.galleryphotos.ui.photos.DirectoryPhotosActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private var binding:ActivityMainBinding? = null
    private val TAG = "MainActivity"
    private val mDirectoryAdapter by lazy{
        DirectoriesAdapter(ArrayList()){ arr , dirName ->
            DirectoryPhotosActivity.startIntent(this@MainActivity, arr, dirName)
        }
    }
    val mViewModel by viewModels<DirectoryViewModel>()
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
        mViewModel.directoriesResponse.observe(this, Observer {
            it?:return@Observer
            mDirectoryAdapter.updateData(directoriesList = it)
            Log.d(TAG, "initArguments: ArrrayImages  $it")
        })
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
            mViewModel?.getDirectories()
        }
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
                        mViewModel?.getDirectories()
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