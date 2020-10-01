package com.miguelete.data.repository

import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

class TwitterRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val locationRepository: LocationRepository
) {

    companion object{
        private const val MILLISECONDS_TO_CLEAR :Long = 5000
    }

    fun getTweets(query: String? = null) : Flow<List<Tweet>> {
        return if (query.isNullOrEmpty()){
            localDataSource.getTweets()
        } else {
            localDataSource.getTweetsContaining(query)
            localDataSource.getTweets()
        }
    }

    suspend fun getRecentTweets(query: String) {
        val remoteTweets = remoteDataSource.getRecentTweets(
            locationRepository.findLastLocation(),
            query
        )
        deleteOldTweets(MILLISECONDS_TO_CLEAR)
        localDataSource.saveTweets(remoteTweets)
    }

    suspend fun findById(id: Int): Tweet = localDataSource.findById(id)

    suspend fun delete(tweet: Tweet) = localDataSource.deleteTweet(tweet)

    suspend fun deleteOldTweets(millisToDelete: Long) = localDataSource.deleteOldTweets(millisToDelete)
}