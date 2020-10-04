package com.miguelete.twitterpins.data

import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.db.Tweet as RoomTweet
import twitter4j.GeoLocation
import twitter4j.Status
import java.text.SimpleDateFormat
import java.util.*


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

fun Status.toDomainTweet() : Tweet = Tweet(
    id,
    text,
    createdAt.toSimpleDate(),
    retweetCount,
    buildLatLongFromGeolocation(geoLocation),
    user.name,
    user.originalProfileImageURL,
    System.currentTimeMillis()
)

fun randomLatLong() : LatLong {
    val r = Random()
    val u: Double = r.nextDouble()
    val v: Double = r.nextDouble()
    val latitude = Math.toDegrees(Math.acos(u * 2 - 1)) - 90
    val longitude = 360 * v - 180
    return LatLong(latitude, longitude)
}

private fun buildLatLongFromGeolocation(geoLocation: GeoLocation?): LatLong {
    return if (geoLocation!=null){
        LatLong(geoLocation.latitude, geoLocation.longitude)
    } else {
        randomLatLong()
    }
}

private fun Date.toSimpleDate() : String {
    val format = SimpleDateFormat("HH:mm dd/MM/yyy", Locale.US)
    return format.format(this)
}
