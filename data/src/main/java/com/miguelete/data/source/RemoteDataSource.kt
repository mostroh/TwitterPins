package com.miguelete.data.source

import com.miguelete.domain.LatLong

interface RemoteDataSource {
    suspend fun getRecentTweets(apiKey: String, secretKey: String, latLong: LatLong)
}