package com.miguelete.twitterpins.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.miguelete.data.LocationDataSource
import com.miguelete.domain.LatLong
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): LatLong? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toLatLong())
                }
        }

    private fun Location?.toLatLong(): LatLong? {
        var location: LatLong? = null
        this?.let {
            location = LatLong(latitude, longitude)
        }
        return location
    }
}