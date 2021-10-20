package com.sanjay.galleryphotos.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.galleryphotos.models.Directory
import com.sanjay.galleryphotos.repository.DirectoryRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DirectoryViewModel @Inject constructor(private val directoryRepositoryImpl: DirectoryRepositoryImpl) : ViewModel() {

    private val _directoriesResponse: MutableLiveData<ArrayList<Directory>> = MutableLiveData()
    val directoriesResponse:MutableLiveData<ArrayList<Directory>> =_directoriesResponse


    fun getDirectories() {
        viewModelScope.launch {
            val directories = directoryRepositoryImpl.getDirectories()
            _directoriesResponse.postValue(directories)
        }
    }

}
