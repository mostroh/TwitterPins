package com.miguelete.data.repository

import com.miguelete.data.source.RemoteDataSource

class TwitterRepository(
    private val remoteDataSource: RemoteDataSource,
    private val locationRepository: LocationRepository,
    private val apiKey: String,
    private val apiSecret: String
) {

    //TODO to be completed
    suspend fun getRecentTweets() {

    }
}