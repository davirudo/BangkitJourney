package com.example.katahatiplus.fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.katahatiplus.R
import com.example.katahatiplus.activity.LoginActivity
import com.example.katahatiplus.databinding.FragmentMapsBinding
import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.response.StoriesResponse
import com.example.katahatiplus.retrofit.ApiConfig
import com.example.katahatiplus.utils.SessionManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.gson.Gson

import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        val token = LoginActivity.sessionManager.getString("TOKEN")
        setAllUserLocation(token.toString())

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapsFragment.getMapAsync(this)
        
        
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        setMapStyle()
//        setAllUserLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_options, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.normal_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                true
            }
            R.id.satellite_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                true
            }
            R.id.terrain_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                true
            }
            R.id.hybrid_type -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    //Menampilkan satu halaman baru berisi peta yang menampilkan semua cerita yang memiliki lokasi dengan benar.
    //Data story yang memiliki lokasi latitude dan longitude dapat diambil melalui parameter location seperti berikut
    //https://story-api.dicoding.dev/v1/stories?location=1
    private fun setAllUserLocation(token: String) {

        val client = ApiConfig.getApiService().getAllUserLocation(token)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
                if (response.isSuccessful) {
                    val stories = response.body()?.listStory?.filter { it.lat != null && it.lon != null }
                    val mapFragment = SupportMapFragment.newInstance()
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(R.id.map, mapFragment)?.commit()
                    mapFragment.getMapAsync { googleMap ->
                        stories?.forEach { story ->
                            val markerOptions = MarkerOptions()
                                .position(LatLng(story.lat, story.lon))
                                .title(story.name)
                            googleMap.addMarker(markerOptions)
                        }
                    }
                } else {
                    Log.e(TAG, "Response failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get stories: ${t.message}")
            }

        })
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getMyLocation()
        }
    }

    //Menampilkan satu halaman baru berisi peta yang menampilkan semua cerita yang memiliki lokasi dengan benar.
        //Data story yang memiliki lokasi latitude dan longitude dapat diambil melalui parameter location seperti berikut
        //https://story-api.dicoding.dev/v1/stories?location=1

        //TODO 13: Panggil fungsi getAllUserLocation() di dalam onMapReady()
        //TODO 14: Buat variabel stories untuk menampung data story yang memiliki lokasi
        //TODO 15: Tambahkan marker untuk setiap data story yang ada di variabel stories
        //TODO 16: Tambahkan animasi kamera untuk menampilkan semua marker yang ada di peta

    companion object {
        lateinit var sessionManager: SessionManager
        private lateinit var context: Context
    }
}