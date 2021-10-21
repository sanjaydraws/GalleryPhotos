package com.sanjay.galleryphotos.ui.photos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.adapters.DirectoryPhotosAdapter
import com.sanjay.galleryphotos.databinding.ActivityDirectoryPhotosBinding
import com.sanjay.galleryphotos.ui.base.BaseActivity

class DirectoryPhotosActivity : BaseActivity() {
    private var binding:ActivityDirectoryPhotosBinding? = null
    private val mDirectoryPhotosAdapter by lazy{
        DirectoryPhotosAdapter(ArrayList())
    }
    companion object{
        const val DIRECTORY_PHOTOS = "directoryPhotos"
        const val DIR_NAME = "directoryName"
        fun startIntent(context: Context?,directoryPhotos:ArrayList<String>? = null,dirName:String? =  null  ){
            val intent = Intent(context,DirectoryPhotosActivity::class.java)
            intent.apply {
                putStringArrayListExtra(DIRECTORY_PHOTOS, directoryPhotos)
                putExtra(DIR_NAME, dirName)
            }
            context?.startActivity(intent)
        }
    }
    private val directoryPhotos by  lazy{
        intent.extras?.getStringArrayList(DIRECTORY_PHOTOS)
    }
    private val dirName by lazy{
        intent.extras?.getString(DIR_NAME)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDirectoryPhotosBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            lifecycleOwner = this@DirectoryPhotosActivity
            executePendingBindings()
        }

        init()
    }

    override fun initArguments() {
        binding?.header?.title  = dirName
        setSupportActionBar(binding?.header)
        binding?.photosDirectoryGrid?.adapter = mDirectoryPhotosAdapter
         mDirectoryPhotosAdapter.updateData(directoryPhotos)
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