package com.miguelete.twitterpins.data.db

import com.miguelete.data.source.LocalDataSource
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.toDomainTweet
import com.miguelete.twitterpins.data.toRoomTweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class RoomDataSource(db: TweetDatabase) : LocalDataSource {

    private val tweetDao = db.tweetDao()

    override suspend fun saveTweets(tweets: List<Tweet>) {
        withContext(Dispatchers.IO) {
            tweetDao.insertTweets(tweets.map { it.toRoomTweet() })
        }
    }

    override suspend fun saveTweet(tweet: Tweet) {
        withContext(Dispatchers.IO) {
            tweetDao.insertTweet(tweet.toRoomTweet())
        }
    }

    override fun getTweets(): Flow<List<Tweet>>  =
        tweetDao
            .getAll()
            .map { tweets -> tweets.map { it.toDomainTweet() } }




}