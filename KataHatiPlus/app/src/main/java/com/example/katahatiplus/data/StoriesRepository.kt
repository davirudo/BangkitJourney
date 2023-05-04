package com.example.katahatiplus.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.katahatiplus.database.StoriesDatabase
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.retrofit.ApiService


class StoriesRepository(private val storiesDatabase: StoriesDatabase, private val apiService: ApiService){

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoriesPagingSource(apiService)
            }
        ).liveData
    }
}