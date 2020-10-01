package com.miguelete.twitterpins.data

import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.db.Tweet as RoomTweet
import com.miguelete.twitterpins.data.server.TweetResult

fun TweetResult.toDomainTweet(): Tweet = Tweet(
    id,
    text,
    createdAt,
    retweetCount,
    getCoordinates(this),
    user.name,
    user.profileImageUrl,
    0
)

fun Tweet.toRoomTweet(insertedMillis: Long?) : RoomTweet = RoomTweet(
    id,
    text,
    createdAt,
    retweetCount,
    coordinates.lat,
    coordinates.long,
    user,
    user_image,
    insertedMillis ?: insertedAt
)

fun RoomTweet.toDomainTweet(): Tweet = Tweet(
    id,
    text,
    createdAt,
    retweetCount,
    LatLong(latitude, longitude),
    user,
    userImage,
    insertedMillis
)


private fun getCoordinates(tweetResult: TweetResult) : LatLong {
    var latLong = LatLong(0.0,0.0)
    tweetResult.coordinates?.let {
        latLong = LatLong(it.coordinates.latitude, it.coordinates.longitude)
    }
    tweetResult.geo?.let {
        latLong = LatLong(it.coordinates.latitude, it.coordinates.longitude)
    }
    return latLong
}