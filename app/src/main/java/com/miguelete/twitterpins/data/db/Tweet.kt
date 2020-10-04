package com.miguelete.twitterpins.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tweet(
    @PrimaryKey val id: Long,
    val text: String,
    val createdAt: String,
    val retweetCount: Int,
    val latitude: Double,
    val longitude: Double,
    val user: String,
    val userImage: String,
    val insertedMillis: Long
)