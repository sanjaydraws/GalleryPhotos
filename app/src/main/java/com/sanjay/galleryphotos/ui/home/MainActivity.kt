package com.sanjay.galleryphotos.ui.home

import android.os.Bundle
import com.google.gson.Gson
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.models.Directory
import com.sanjay.galleryphotos.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    var binding:ActivityMainBinding? = null
    val TAG = "MainActivity"
    val mDirectoryAdapter by lazy{
        DirectoriesAdapter(ArrayList())
    }
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

    }

    override fun initViews() {

    }

    override fun setupListener() {
    }

    override fun initObservers() {
    }

    override fun loadData() {
    }

}