package com.sanjay.galleryphotos.ui.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val context: Context
        get() = this

    protected fun init() {
        initArguments()
        initViews()
        setupListener()
        initObservers()
        loadData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    abstract fun initArguments()
    abstract fun initViews()
    abstract fun setupListener()
    abstract fun initObservers()
    abstract fun loadData()

}