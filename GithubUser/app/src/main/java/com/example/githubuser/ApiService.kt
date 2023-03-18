package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{username}")
    @Headers("Authorization: token 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")
    fun getUser(
        @Path("username") username: String
    ): Call<UserResponse>
}

// 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