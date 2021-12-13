package com.example.daggerlatestversion.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerlatestversion.base.NetworkHelper
import com.example.daggerlatestversion.data.User
import com.example.daggerlatestversion.repo.MainRepository
import kotlinx.coroutines.launch
import com.example.daggerlatestversion.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _user = MutableLiveData<Resource<List<User>>>()

    val user: LiveData<Resource<List<User>>>
        get() = _user

    init {
        _user.postValue(Resource.empty())
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _user.postValue(Resource.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                repository.getUsers().let {
                    if (it.isSuccessful) {
                        _user.postValue(Resource.success(it.body()))
                    } else {
                        _user.postValue(Resource.error(it.errorBody().toString()))
                    }
                }
            } else {
                _user.postValue(Resource.error("No or very weak internet connection please check!"))
            }
        }
    }
}