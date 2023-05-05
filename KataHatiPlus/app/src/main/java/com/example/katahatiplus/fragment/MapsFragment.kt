package com.example.katahatiplus.fragment
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.katahatiplus.R
import com.example.katahatiplus.activity.LoginActivity
import com.example.katahatiplus.databinding.FragmentMapsBinding
import com.example.katahatiplus.response.StoriesResponse
import com.example.katahatiplus.retrofit.ApiConfig
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapsFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val token = LoginActivity.sessionManager.getString("TOKEN")
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        setMapStyle()
        setAllUserLocation(token.toString())
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

    private fun setAllUserLocation(token: String) {
        val client = ApiConfig.getApiService().getAllUserLocation(token)
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(call: Call<StoriesResponse>, response: Response<StoriesResponse>) {
                if (response.isSuccessful) {
                    val stories = response.body()?.listStory?.filter { true }
                        stories?.forEach { story ->
                            val markerOptions = MarkerOptions()
                                .position(LatLng(story.lat, story.lon))
                                .title(story.name)
                                .snippet(story.description)
                            boundsBuilder.include(markerOptions.position)
                            val bounds = boundsBuilder.build()
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300))
                            mMap.addMarker(markerOptions)
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
}