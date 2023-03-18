package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _user = MutableLiveData<ItemsItem>()
    val user: LiveData<ItemsItem> = _user

    private val _listUser = MutableLiveData<ArrayList<ItemsItem>>()
    val listUser: LiveData<ArrayList<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        private const val USERNAME = "David"
    }

    init {
        findUser()
    }

    private fun findUser() {
        isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USERNAME)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                isLoading.value = false
                if(response.isSuccessful) {
                    _listUser.value = response.body()?.items
                } else {
                    Log.(TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable){
                isLoading.value = false
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