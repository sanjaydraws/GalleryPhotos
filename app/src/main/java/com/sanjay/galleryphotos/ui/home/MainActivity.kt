package com.sanjay.galleryphotos.ui.home

import android.os.Bundle
import com.sanjay.galleryphotos.databinding.ActivityMainBinding
import com.sanjay.galleryphotos.ui.base.BaseActivity
import com.simplemobiletools.commons.views.MyRecyclerView

class MainActivity : BaseActivity() {
    var binding:ActivityMainBinding? = null
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