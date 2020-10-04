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

    suspend fun getTweets(query: String) : Flow<List<Tweet>>  =
        remoteDataSource.getStreamAsFlow(query)
            .flowOn(Dispatchers.Default)
            .map { tweetList ->
                localDataSource.saveTweets(tweetList)
            }
            .combine(localDataSource.getTweetsContaining(query)) { remote, local ->
                local
            }
            .flowOn(Dispatchers.IO)
            .conflate()


    suspend fun observeTweets(query: String) : Flow<Tweet> =
        remoteDataSource.getStreamTweet(query)


    suspend fun disconnectStream() {
        remoteDataSource.disconnectStream()
    }


    suspend fun findById(id: Int): Tweet = localDataSource.findById(id)

    suspend fun delete(tweet: Tweet) = localDataSource.deleteTweet(tweet)

    suspend fun deleteOldTweets(millisToDelete: Long) = localDataSource.deleteOldTweets(millisToDelete)
}