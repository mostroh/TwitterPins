package com.miguelete.domain

data class Tweet(
    val id: Int,
    val text: String,
    val createdAt: String,
    val retweetCount: Int,
    val coordinates: LatLong,
    val user: String,
    val user_image: String,
    val insertedAt: Long
)