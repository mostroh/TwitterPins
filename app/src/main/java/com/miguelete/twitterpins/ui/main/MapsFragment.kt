package com.miguelete.twitterpins.ui.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.EventLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.ui.common.Event
import com.miguelete.twitterpins.ui.model.Tweet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class MapsFragment : Fragment() {

    companion object{
        const val MILLISECONNDS_TO_SHOW = 30000L
    }

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var map : GoogleMap
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.lastTweet.observe(viewLifecycleOwner, Observer { tweet ->
            addMarker(tweet)
        })
    }

    private fun addMarker(tweet: Tweet) {
        if (mapReady){
            viewLifecycleOwner.lifecycleScope.launch {
                val markerPosition = LatLng(tweet.latitude, tweet.longitude)
                val marker = map.addMarker(MarkerOptions().position(markerPosition))
                marker.tag = tweet
                delay(MILLISECONNDS_TO_SHOW)
                marker.remove()
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mapReady = true
        val sydney = LatLng(-34.0, 151.0)
        map = googleMap
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        map.setOnMarkerClickListener { marker->
            val tweet :Tweet= marker.tag as Tweet
            viewModel.onTweetClicked(tweet)
            true
        }

    }
}