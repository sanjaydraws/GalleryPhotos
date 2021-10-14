package com.sanjay.galleryphotos.helper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Config (@ApplicationContext context: Context):BaseConfig(context){


}