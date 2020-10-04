package com.miguelete.data.source

import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveTweets(tweets: List<Tweet>)
    suspend fun saveTweet(tweet: Tweet)
    fun getTweets() : Flow<List<Tweet>>
}