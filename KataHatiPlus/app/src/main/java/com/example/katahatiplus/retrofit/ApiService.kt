package com.example.katahatiplus.retrofit

import com.example.katahatiplus.response.*
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

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>


    @GET("stories")
    suspend fun getAllStoriesPaging(
        @Header("Authorization") token: String,
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("location") location: Int = 1
    ): StoriesResponse

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

    @GET("stories")
    fun getAllUserLocation(
        @Header("Authorization") token: String,
        @Query("location") location: Int = 1
    ): Call<StoriesResponse>
}