package com.dicoding.myunlimitedquotes.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dicoding.myunlimitedquotes.database.QuoteDatabase
import com.dicoding.myunlimitedquotes.network.ApiService
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

@OptIn(ExperimentalPagingApi::class)
class QuoteRemoteMediator (

    private val databse: QuoteDatabase,
    private val apiService: ApiService
        ) : RemoteMediator<Int, QuoteResponseItem>() {
    
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, QuoteResponseItem>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

}