package com.miguelete.twitterpins.data.server

import androidx.lifecycle.LiveData
import com.miguelete.twitterpins.data.GenericApiResponse
import retrofit2.http.Body
import retrofit2.http.GET

interface TwitterDBService {

    @GET("statuses/filter.json")
    fun retrieveFilteredTweets(
        @Body twitterFilterDto: TwitterFilterDto
    ): LiveData<GenericApiResponse<List<TweetResult>>>
}