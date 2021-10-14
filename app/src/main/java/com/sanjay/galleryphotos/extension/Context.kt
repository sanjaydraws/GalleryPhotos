package com.sanjay.galleryphotos.extension

import android.content.Context
import android.os.Process
import com.sanjay.galleryphotos.models.Directory
//
//fun Context.getCachedDirectories(getVideosOnly: Boolean = false, getImagesOnly: Boolean = false, forceShowHidden: Boolean = false, callback: (ArrayList<Directory>) -> Unit) {
//
//    ensureBackgroundThread {
//        try {
//            Process.setThreadPriority(Process.THREAD_PRIORITY_MORE_FAVORABLE)
//        } catch (ignored: Exception) {
//        }
//
//        val directories = try {
////            directoryDao.getAll() as ArrayList<Directory>
//        } catch (e: Exception) {
//            ArrayList<Directory>()
//        }
//
////        if (!config.showRecycleBinAtFolders) {
////            directories.removeAll { it.isRecycleBin() }
////        }
//
////        val shouldShowHidden = config.shouldShowHidden || forceShowHidden
////        val excludedPaths = config.excludedFolders
////        val includedPaths = config.includedFolders
//
//        val folderNoMediaStatuses = HashMap<String, Boolean>()
//        val noMediaFolders = getNoMediaFoldersSync()
//        noMediaFolders.forEach { folder ->
//            folderNoMediaStatuses["$folder/$NOMEDIA"] = true
//        }
//
//        var filteredDirectories = directories.filter {
//            it.path.shouldFolderBeVisible(excludedPaths, includedPaths, shouldShowHidden, folderNoMediaStatuses) { path, hasNoMedia ->
//                folderNoMediaStatuses[path] = hasNoMedia
//            }
//        } as ArrayList<Directory>
//        val filterMedia = config.filterMedia
//
//        filteredDirectories = (when {
//            getVideosOnly -> filteredDirectories.filter { it.types and TYPE_VIDEOS != 0 }
//            getImagesOnly -> filteredDirectories.filter { it.types and TYPE_IMAGES != 0 }
//            else -> filteredDirectories.filter {
//                (filterMedia and TYPE_IMAGES != 0 && it.types and TYPE_IMAGES != 0) ||
//                        (filterMedia and TYPE_VIDEOS != 0 && it.types and TYPE_VIDEOS != 0) ||
//                        (filterMedia and TYPE_GIFS != 0 && it.types and TYPE_GIFS != 0) ||
//                        (filterMedia and TYPE_RAWS != 0 && it.types and TYPE_RAWS != 0) ||
//                        (filterMedia and TYPE_SVGS != 0 && it.types and TYPE_SVGS != 0) ||
//                        (filterMedia and TYPE_PORTRAITS != 0 && it.types and TYPE_PORTRAITS != 0)
//            }
//        }) as ArrayList<Directory>
//
//        if (shouldShowHidden) {
//            val hiddenString = resources.getString(R.string.hidden)
//            filteredDirectories.forEach {
//                val noMediaPath = "${it.path}/$NOMEDIA"
//                val hasNoMedia = if (folderNoMediaStatuses.keys.contains(noMediaPath)) {
//                    folderNoMediaStatuses[noMediaPath]!!
//                } else {
//                    it.path.doesThisOrParentHaveNoMedia(folderNoMediaStatuses) { path, hasNoMedia ->
//                        val newPath = "$path/$NOMEDIA"
//                        folderNoMediaStatuses[newPath] = hasNoMedia
//                    }
//                }
//
//                it.name = if (hasNoMedia && !it.path.isThisOrParentIncluded(includedPaths)) {
//                    "${it.name.removeSuffix(hiddenString).trim()} $hiddenString"
//                } else {
//                    it.name.removeSuffix(hiddenString).trim()
//                }
//            }
//        }
//
//        val clone = filteredDirectories.clone() as ArrayList<Directory>
//        callback(clone.distinctBy { it.path.getDistinctPath() } as ArrayList<Directory>)
//        removeInvalidDBDirectories(filteredDirectories)
//    }
//}
