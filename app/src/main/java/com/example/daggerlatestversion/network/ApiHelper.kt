package com.example.daggerlatestversion.network

import com.example.daggerlatestversion.data.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}