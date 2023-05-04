package com.example.katahatiplus.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.katahatiplus.database.StoriesDatabase
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.retrofit.ApiService


class StoriesRepository(private val storiesDatabase: StoriesDatabase, private val apiService: ApiService){
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoriesRemoteMediator(storiesDatabase, apiService, token),
            pagingSourceFactory = {
            storiesDatabase.storiesDao().getAllStories()
            }
        ).liveData
    }
}