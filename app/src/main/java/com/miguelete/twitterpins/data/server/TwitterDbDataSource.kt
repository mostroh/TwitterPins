package com.miguelete.twitterpins.data.server

import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.toDomainTweet

class TwitterDbDataSource(private val twitterDB: TwitterDB) : RemoteDataSource {

    override suspend fun getRecentTweets(apiKey: String,
                                         secretKey: String,
                                         latLong: LatLong,
                                         query: String) : List<Tweet> =
        twitterDB.service
            .retrieveFilteredTweets(TwitterFilterDto(query, latLong.lat, latLong.long))
            .filter {
                it.geo !=null || it.coordinates!= null
            }
            .map{
                it.toDomainTweet()
            }
}