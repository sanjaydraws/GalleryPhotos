package com.sanjay.galleryphotos.ui.photos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.adapters.DirectoryPhotosAdapter
import com.sanjay.galleryphotos.databinding.ActivityDirectoryPhotosBinding
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.ui.base.BaseActivity

class DirectoryPhotosActivity : BaseActivity() {
    private var binding:ActivityDirectoryPhotosBinding? = null
    private val mDirectoryPhotosAdapter by lazy{
        DirectoryPhotosAdapter(ArrayList())
    }
    companion object{
        const val DIRECTORY_PHOTOS = "directoryPhotos"
        fun startIntent(context: Context?,directoryPhotos:ArrayList<String>? = null ){
            val intent = Intent(context,DirectoryPhotosActivity::class.java)
            intent.apply {
                putStringArrayListExtra(DIRECTORY_PHOTOS, directoryPhotos)
            }
            context?.startActivity(intent)
        }
    }
    private val directoryPhotos by  lazy{
        intent.extras?.getStringArrayList(DIRECTORY_PHOTOS)
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