package com.sanjay.galleryphotos.repository

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.sanjay.galleryphotos.constants.BY_MEDIA_DATE_TAKEN
import com.sanjay.galleryphotos.models.Directory
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DirectoryRepositoryImpl @Inject constructor(@ApplicationContext val context: Context?) :DirectoryRepository{

    override fun getDirectories(): ArrayList<Directory> {
        val directoriesList:ArrayList<Directory> = ArrayList()
        var int_position = 0
        var isDirectoryAdded = false
        var absolutePathOfImage: String? = null
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val cursor: Cursor? = context?.contentResolver?.query(uri, projection, null, null, "$BY_MEDIA_DATE_TAKEN DESC")
        val columnIndexData: Int? = cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA) // 0
        val columnIndexDirectoryName: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME) // 1
        while (cursor?.moveToNext() == true) {
            absolutePathOfImage = columnIndexData?.let { cursor.getString(it) } // storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20211018-WA0001.jpg
            columnIndexDirectoryName?.let { cursor.getString(it) }?.let { Log.e("Folder", it) }
            for (i in 0 until directoriesList.size) {
                if (directoriesList[i].directoryName.equals(columnIndexDirectoryName?.let { cursor.getString(it) })) {
                    // if directory already present in list
                    isDirectoryAdded = true
                    int_position = i
                    break
                } else {
                    // if size is zero or directory not present in list
                    isDirectoryAdded = false
                }
            }
            if (isDirectoryAdded) {
                // when directory already present
                val photosPath: ArrayList<String>? = ArrayList()
                directoriesList[int_position].allPhotos?.let { photosPath?.addAll(it) } // added to new array
                absolutePathOfImage?.let { photosPath?.add(it) } // added new path
                directoriesList[int_position].allPhotos = photosPath // copy on that index
            } else {
                //if size is zero or directory not present in list and  added new directory in list
                val photosPath: ArrayList<String>? = ArrayList()
                absolutePathOfImage?.let { photosPath?.add(it) }
                val directory = Directory()
                directory.directoryName = columnIndexDirectoryName?.let { cursor.getString(it) }
                directory.allPhotos = photosPath
                directoriesList.add(directory)
            }
        }
//        for (i in 0 until directoriesList.size) {
//            directoriesList[i].directoryName?.let { Log.e("FOLDER", it) }
//            for (j in 0 until directoriesList[i].allPhotos?.size!!) {
//                directoriesList[i].allPhotos?.get(j)?.let { Log.e("FILE", it) }
//            }
//        }
        return directoriesList

    }


}