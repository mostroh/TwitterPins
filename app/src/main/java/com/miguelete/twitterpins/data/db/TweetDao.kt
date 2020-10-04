package com.miguelete.twitterpins.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetDao {

    @Query("SELECT * FROM Tweet")
    fun getAll(): Flow<List<Tweet>>

    @Query("SELECT * FROM Tweet WHERE text LIKE :query")
    fun getAllContaining(query: String) : Flow<List<Tweet>>

    @Query("SELECT * FROM Tweet WHERE id = :id")
    fun findById(id: Int): Tweet

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTweet(toRoomTweet: Tweet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTweets(tweets: List<Tweet>)

    @Delete
    suspend fun deleteTweet(tweet: Tweet)

}