package com.miguelete.data.source

import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveTweets(tweets: List<Tweet>)
    suspend fun saveTweet(tweet: Tweet)
    suspend fun deleteTweet(tweet: Tweet)
    suspend fun deleteOldTweets(millisToDelete: Long)
    suspend fun findById(id: Int) : Tweet
    fun getTweets() : Flow<List<Tweet>>
    fun getTweetsContaining(query : String) :Flow<List<Tweet>>
}