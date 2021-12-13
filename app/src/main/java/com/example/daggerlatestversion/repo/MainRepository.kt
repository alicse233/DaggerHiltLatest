package com.example.daggerlatestversion.repo

import com.example.daggerlatestversion.network.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()
}