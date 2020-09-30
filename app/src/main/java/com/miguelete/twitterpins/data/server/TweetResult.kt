package com.miguelete.twitterpins.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TweetResult(
    @SerializedName("id") val id: Int,
    @SerializedName("text") val text: String,
    @SerializedName("retweet_count") val retweetCount: Int,
    @SerializedName("geo") val geo: TweetGeoDto?,
    @SerializedName("coordinates") val coordinates: TweetGeoDto?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("user") val user: TwitterUserDto
) : Parcelable