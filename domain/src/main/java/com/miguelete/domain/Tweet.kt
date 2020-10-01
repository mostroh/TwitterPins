package com.miguelete.domain

data class Tweet(
    val id: Int,
    val text: String,
    val retweetCount: Int,
    val latLong: LatLong,
    val user: String,
    val user_image: String
)