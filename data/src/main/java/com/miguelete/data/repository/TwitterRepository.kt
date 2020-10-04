package com.miguelete.data.repository

import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.Tweet
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class TwitterRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val locationRepository: LocationRepository
) {

    suspend fun observeTweets(query: String) : Flow<Tweet> =
        remoteDataSource.getStreamTweet(query)


    suspend fun disconnectStream() {
        remoteDataSource.disconnectStream()
    }


}