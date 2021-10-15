package com.sanjay.galleryphotos.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.sanjay.galleryphotos.constants.PERMISSION_READ_STORAGE
import com.sanjay.galleryphotos.constants.PERMISSION_WRITE_STORAGE


fun Context.hasPermission(permissionId: Int) = ContextCompat.checkSelfPermission(this, getPermissionString(permissionId)) == PackageManager.PERMISSION_GRANTED



fun Context.getPermissionString(id: Int) = when (id) {
    PERMISSION_READ_STORAGE -> Manifest.permission.READ_EXTERNAL_STORAGE
    PERMISSION_WRITE_STORAGE -> Manifest.permission.WRITE_EXTERNAL_STORAGE
    else -> ""
}