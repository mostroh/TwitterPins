package com.miguelete.data.repository

import com.miguelete.data.LocationDataSource
import com.miguelete.domain.LatLong


class LocationRepository(
    private val locationDataSource: LocationDataSource,
    private val  permissionChecker: PermissionChecker) {

    companion object {
        private val DEFAULT_LATLONG: LatLong = LatLong(40.416775, -3.703790)
    }

    suspend fun findLastLocation() : LatLong {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastLocation() ?: DEFAULT_LATLONG
        } else {
            DEFAULT_LATLONG
        }
    }
}