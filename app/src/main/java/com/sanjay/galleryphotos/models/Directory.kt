package com.sanjay.galleryphotos.models



data class Directory(
    val id:Int? = null,
    var str_folder:String? = null,
    var al_imagepath:ArrayList<String>? = ArrayList()

)