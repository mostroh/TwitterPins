package com.miguelete.data.source

import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getStreamTweet(query: String) : Flow<Tweet>

    suspend fun disconnectStream()
}