package com.example.katahatiplus.injection

import android.content.Context
import com.example.katahatiplus.data.StoriesRepository
import com.example.katahatiplus.database.StoriesDatabase
import com.example.katahatiplus.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoriesRepository {
        val database = StoriesDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoriesRepository(database, apiService)
    }
}