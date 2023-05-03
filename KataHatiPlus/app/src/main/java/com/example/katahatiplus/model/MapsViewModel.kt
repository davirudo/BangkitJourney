package com.example.katahatiplus.model

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.katahatiplus.fragment.MapsFragment
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.response.StoriesResponse
import com.example.katahatiplus.retrofit.ApiConfig
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val boundsBuilder = LatLngBounds.Builder()
    private val _listMaps = MutableLiveData<List<ListStoryItem>>()
    val listMap: LiveData<List<ListStoryItem>> = _listMaps

    companion object {
        private const val TAG = "MapsFragment"
    }

    fun getAllUserLocation(token: String) {
        val client = ApiConfig.getApiService().getAllUserLocation(token)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listMaps.value = response.body()?.listStory
                    }
                } else {
                    Log.e(TAG, "OnResponse ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "Failed", t)
            }

        })
    }
}