package com.example.katahatiplus.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.response.StoriesResponse
import com.example.katahatiplus.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoriesViewModel : ViewModel() {


    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "StoriesFragment"
    }

    fun getAllStories(token : String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getAllStories(token)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listStory.value = response.body()?.listStory
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
                //add Toast
            }
            })
    }
}