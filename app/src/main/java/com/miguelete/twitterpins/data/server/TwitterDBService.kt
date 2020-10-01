package com.miguelete.twitterpins.data.server

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TwitterDBService {

    @POST("statuses/filter.json")
    suspend fun retrieveFilteredTweets(
        @Body twitterFilterDto: TwitterFilterDto):List<TweetResult>
}