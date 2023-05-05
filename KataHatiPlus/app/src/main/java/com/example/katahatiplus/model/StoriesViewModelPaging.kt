package com.example.katahatiplus.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.katahatiplus.data.StoriesRepository
import com.example.katahatiplus.injection.Injection
import com.example.katahatiplus.response.ListStoryItem

class StoriesViewModelPaging(private val storiesRepository: StoriesRepository): ViewModel() {

    fun getStories(token: String) : LiveData<PagingData<ListStoryItem>> =
        storiesRepository.getStories(token).cachedIn(viewModelScope)
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoriesViewModelPaging::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoriesViewModelPaging(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}