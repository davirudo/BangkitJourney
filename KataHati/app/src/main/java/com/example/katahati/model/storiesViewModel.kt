package com.example.katahati.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.katahati.response.ListStoryItem

class storiesViewModel() : ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: MutableLiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "StoriesFragment"
    }

    init {
        setUserData(listOf())
    }

    fun setUserData(data: List<ListStoryItem>) {
        _isLoading.value = true
        _listStory.value = data
    }
}