package com.miguelete.twitterpins.ui.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tweet(
    val text: String,
    val createdAt: String,
    val retweetCount: Int,
    val latitude: Double,
    val longitude: Double,
    val user: String,
    val user_image: String
) : Parcelable