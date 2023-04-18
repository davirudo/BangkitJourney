package com.example.katahati.retrofit

import com.example.katahati.response.LoginResponse
import com.example.katahati.response.StoriesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String,
        @Query("size") size: Int? = 15,
    ): Call<StoriesResponse>
}