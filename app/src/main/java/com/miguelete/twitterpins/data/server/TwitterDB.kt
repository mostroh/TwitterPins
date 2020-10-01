package com.miguelete.twitterpins.data.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TwitterDB(baseUrl: String, okHttpClient: OkHttpClient) {

    val service: TwitterDBService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .run {
            create(TwitterDBService::class.java)
        }
}