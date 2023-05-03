package com.example.katahatiplus.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.retrofit.ApiService

class StoriesPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        TODO("Not yet implemented")
    }


}