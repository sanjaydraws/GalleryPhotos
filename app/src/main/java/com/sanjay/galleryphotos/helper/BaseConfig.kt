package com.sanjay.galleryphotos.helper

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class BaseConfig (@ApplicationContext context: Context?) {
      @Inject
      protected lateinit var  sharePrefs:SharedPreferences

}