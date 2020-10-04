package com.miguelete.data.repository

import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.*

class TwitterRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val locationRepository: LocationRepository
) {

    fun getTweets(query: String? = null) : Flow<List<Tweet>> {
        return if (query.isNullOrEmpty()){
            localDataSource.getTweets()
        } else {
            localDataSource.getTweetsContaining(query)
            localDataSource.getTweets()
        }
    }

    suspend fun connectStreamAndUpdate(query: String): Flow<List<Tweet>> = remoteDataSource.getStreamAsFlow(query)
//    {
//        remoteDataSource.getStreamAsFlow(query)
//            .catch {
//                    throwable -> println("Error in Repository: ${throwable.message}")
//            }
//            .collect {
//                localDataSource.saveTweets(it)
//            }
//    }

    suspend fun disconnectStream() {
        remoteDataSource.disconnectStream()
    }


    suspend fun findById(id: Int): Tweet = localDataSource.findById(id)

    suspend fun delete(tweet: Tweet) = localDataSource.deleteTweet(tweet)

    suspend fun deleteOldTweets(millisToDelete: Long) = localDataSource.deleteOldTweets(millisToDelete)
}