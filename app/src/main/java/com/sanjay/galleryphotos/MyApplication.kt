package com.sanjay.galleryphotos

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : MultiDexApplication() {

}