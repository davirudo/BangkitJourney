package com.example.katahati.retrofit

import com.example.katahati.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @GET("stories/{id}")
    fun getDetail(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailResponse>

    @Multipart
    @POST("stories")
    fun addNewStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<AddResponse>
}