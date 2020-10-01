package com.miguelete.usecases

import com.miguelete.data.repository.TwitterRepository
import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

class GetRecentTweets(private val twitterRepository: TwitterRepository) {
    suspend fun invoke(query :String): Flow<List<Tweet>> {
        twitterRepository.getRecentTweets(query)
        return twitterRepository.getTweets(query)
    }
}