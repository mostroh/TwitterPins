package com.miguelete.twitterpins.data

import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.server.TweetResult

fun TweetResult.toDomainTweet(): Tweet = Tweet(
    id,
    text,
    retweetCount,
    getCoordinates(this),
    user.name,
    user.profileImageUrl
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