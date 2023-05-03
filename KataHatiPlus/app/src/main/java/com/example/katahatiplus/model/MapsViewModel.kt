package com.example.katahatiplus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.katahatiplus.response.ListStoryItem

class MapsViewModel : ViewModel() {

    private val _listMaps = MutableLiveData<List<ListStoryItem>>()
    val listMap: LiveData<List<ListStoryItem>> = _listMaps

    companion object {
        private const val TAG = "MapsFragment"
    }

    private fun getAllUserLocation() {

//        val apiService = ApiConfig.getApiService()
//        val
//
//        val url = "https://story-api.dicoding.dev/v1/stories?location=1"
//        val request = Request.Builder().url(url).build()
//
//        val client = okhttp3.OkHttpClient()
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e(TAG, "Failed", e)
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (!response.isSuccessful) {
//                    Log.e(TAG, "Failed to get stories. Code: ${response.code}")
//                    return
//                }
//
//                val responseBody = response.body?.string()
//                val gson = Gson()
//                val stories = gson.fromJson(responseBody, StoriesResponse::class.java)
//
//                val storiesLocation = stories.listStory.filter { it.lat != null && it.lon != null }
//
//                activity?.runOnUiThread {
//                    storiesLocation.forEach { story ->
//                        val latLng = LatLng(story.lat as Double, story.lon as Double)
//                        mMap.addMarker(MarkerOptions().position(latLng).title(story.name))
////                        snippet(story.description))
//                        boundsBuilder.include(latLng)
//                    }
//
//                    val bounds: LatLngBounds = boundsBuilder.build()
//                    mMap.animateCamera(
//                        CameraUpdateFactory.newLatLngBounds(
//                            bounds,
//                            resources.displayMetrics.widthPixels,
//                            resources.displayMetrics.heightPixels,
//                            300
//                        )
//                    )
//                }
//            }
//
//        })
//    }
    }
}