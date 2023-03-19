package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    ////////////////////////////////////////////////////////

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowed(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}

// 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