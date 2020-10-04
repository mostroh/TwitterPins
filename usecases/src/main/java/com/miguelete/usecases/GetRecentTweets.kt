package com.miguelete.usecases

import com.miguelete.data.repository.TwitterRepository
import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

class GetRecentTweets(private val twitterRepository: TwitterRepository) {

    suspend fun observeTweets(query:String): Flow<Tweet>
            = twitterRepository.observeTweets(query)

    suspend fun disconnectStream() {
        twitterRepository.disconnectStream()
    }

}