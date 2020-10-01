package com.miguelete.twitterpins.data.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
//TODO Cambiar parámetros de filtrado por los necesarios
data class TwitterFilterDto(
    @SerializedName("track") val track : String,
    @SerializedName("locations") val locations: String

) : Parcelable