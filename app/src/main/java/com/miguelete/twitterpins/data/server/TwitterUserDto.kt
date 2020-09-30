package com.miguelete.twitterpins.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TwitterUserDto(
    @SerializedName("name") val name: String,
    @SerializedName("profile_image_url") val profileImageUrl: String
) : Parcelable