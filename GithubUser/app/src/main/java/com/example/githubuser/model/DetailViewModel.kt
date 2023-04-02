package com.example.githubuser.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.retrofit.ApiConfig
import com.example.githubuser.ItemsItem
import com.example.githubuser.database.Fav
import com.example.githubuser.repository.FavRepository
import com.example.githubuser.response.DetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followerUser = MutableLiveData<List<ItemsItem>>()
    val followerUser: LiveData<List<ItemsItem>> = _followerUser

    private val _followedUser = MutableLiveData<List<ItemsItem>>()
    val followedUser: LiveData<List<ItemsItem>> = _followedUser

    private val mFavRepository: FavRepository = FavRepository(application)

    companion object {
        private const val TAG = "DetailActivity"
    }

    init {
        detailUser()
    }

    fun detailUser(q : String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(q)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun detailFollower(q : String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(q)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followerUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun detailFollowed(q : String = "") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowed(q)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followedUser.value = response.body()
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable){
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    //tambahin insert fav
    fun addFav(fav: Fav) {
        mFavRepository.insert(fav)
    }

    fun deleteFav(fav: Fav) {
        mFavRepository.delete(fav)
    }

    //memanggil semua fav berdasarkan username
    fun getFavByUser(fav: String): LiveData<Fav> = mFavRepository.getFavByUser(fav)

}