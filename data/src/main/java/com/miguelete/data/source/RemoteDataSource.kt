package com.miguelete.data.source

import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getStreamAsFlow(query: String) : Flow<List<Tweet>>

    suspend fun disconnectStream()
}