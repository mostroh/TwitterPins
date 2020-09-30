package com.miguelete.data

import com.miguelete.domain.LatLong

interface LocationDataSource {
    suspend fun findLastLocation() : LatLong?
}