package com.example.katahati.retrofit

import com.example.katahati.response.LoginResponse
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("v1/login")
    fun login(
        @Part email: String,
        @Part password: String
    ): Call<LoginResponse>
}