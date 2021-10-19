package com.sanjay.galleryphotos.models



data class Directory(
    val id:Int? = null,
    var directoryName:String? = null,
    var allPhotos:ArrayList<String>? = ArrayList()

)