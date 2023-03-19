package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainActivity"
        private const val USERNAME = "David"
    }

    init {
        findUser()
    }

    private fun findUser(q : String = "David") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(q)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = response.body()?.items
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }
}

//client.enqueue(object : Callback<UserResponse>) {
//            override fun onResponse(
//                call: Call<UserResponse>
//                response: Response<UserResponse>
//            ) {
//                loading.value = false
//                if(response.isSuccessful) {
//                    _listUser.value = response.body()?.items
//                } else {
//                    Log.(TAG, "onFailure : ${response.message}")
//                }
//            }
//            override fun onFailure(call: Call<UserResponse>, t: Throwable){
//                loading.value = false
//                Log.e(TAG, "onFailure : ${t.message.toString()}")
//            }
//        }