package com.miguelete.data.source

import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet

interface RemoteDataSource {
    suspend fun getRecentTweets(apiKey: String,
                                secretKey: String,
                                latLong: LatLong,
                                query: String) : List<Tweet>
}