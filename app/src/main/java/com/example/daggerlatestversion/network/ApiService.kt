package com.example.daggerlatestversion.network

import com.example.daggerlatestversion.data.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUser(): Response<List<User>>
}