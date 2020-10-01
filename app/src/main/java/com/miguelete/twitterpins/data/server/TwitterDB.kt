package com.miguelete.twitterpins.data.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TwitterDB(baseUrl: String) {

    val service: TwitterDBService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create(TwitterDBService::class.java)
        }
}