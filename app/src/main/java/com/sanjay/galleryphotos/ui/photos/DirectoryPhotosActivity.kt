package com.sanjay.galleryphotos.ui.photos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sanjay.galleryphotos.R
import com.sanjay.galleryphotos.adapters.DirectoriesAdapter
import com.sanjay.galleryphotos.adapters.DirectoryPhotosAdapter
import com.sanjay.galleryphotos.ui.base.BaseActivity

class DirectoryPhotosActivity : BaseActivity() {
    private val mDirectoryPhotosAdapter by lazy{
        DirectoryPhotosAdapter(ArrayList())
    }
    companion object{
        fun startIntent(context: Context?){
            val intent = Intent(context,DirectoryPhotosActivity::class.java)
            context?.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory_photos)
    }

    override fun initArguments() {
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