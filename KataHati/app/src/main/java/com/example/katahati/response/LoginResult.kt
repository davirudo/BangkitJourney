package com.example.katahati.response

data class LoginResult(
    val name: String,
    val token: String,
    val userId: String
)