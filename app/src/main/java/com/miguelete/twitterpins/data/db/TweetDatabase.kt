package com.miguelete.twitterpins.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Tweet::class], version = 1, exportSchema = false )
abstract class TweetDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao
}