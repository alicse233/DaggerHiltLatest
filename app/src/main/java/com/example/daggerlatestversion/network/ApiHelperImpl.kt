package com.example.daggerlatestversion.network

import com.example.daggerlatestversion.data.User
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getUsers(): Response<List<User>> {
        return apiService.getUser()
    }
}