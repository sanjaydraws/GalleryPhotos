package com.sanjay.galleryphotos.repository

import com.sanjay.galleryphotos.models.Directory

interface DirectoryRepository {
    fun getDirectories():ArrayList<Directory>
}