package com.miguelete.twitterpins.data.server

import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.toDomainTweet
import okhttp3.OkHttpClient

class TwitterDbDataSource(private val twitterDB: TwitterDB) : RemoteDataSource {

    companion object{
        private const val LONGITUDE_BOUND_DIFF = 0.008
        private const val LATITUDE_BOUND_DIFF = 0.01
    }

    override suspend fun getRecentTweets(latLong: LatLong, query: String) : List<Tweet> =

        twitterDB.service
            .retrieveFilteredTweets(TwitterFilterDto(query, buildLocations(latLong)))
            .filter {
                it.geo !=null || it.coordinates!= null
            }
            .map{
                it.toDomainTweet()
            }

    private fun buildLocations(latLong: LatLong) = "${latLong.lat - LATITUDE_BOUND_DIFF}," +
            "${latLong.long - LONGITUDE_BOUND_DIFF}," +
            "${latLong.lat + LATITUDE_BOUND_DIFF}," +
            "${latLong.long + LONGITUDE_BOUND_DIFF}"
}