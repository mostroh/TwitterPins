package com.miguelete.twitterpins.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TweetGeoDto(
    @SerializedName("type") val type: String?,
    @SerializedName("coordinates") val coordinates: TweetCoordinatesDto
) : Parcelable