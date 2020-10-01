package com.miguelete.twitterpins.data.server

import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.LatLong

class TwitterDbDataSource(private val twitterDB: TwitterDB) : RemoteDataSource {

    override suspend fun getRecentTweets(apiKey: String, secretKey: String, latLong: LatLong) {
        //TODO reemplazar posteriormente
        twitterDB.service
            .retrieveFilteredTweets(TwitterFilterDto(latLong.lat, latLong.long))

    }
}