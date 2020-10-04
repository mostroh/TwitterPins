package com.miguelete.usecases

import com.miguelete.data.repository.TwitterRepository
import com.miguelete.domain.Tweet
import kotlinx.coroutines.flow.Flow

class GetRecentTweets(private val twitterRepository: TwitterRepository) {

    suspend fun invoke(query:String): Flow<List<Tweet>>
    = twitterRepository.getTweets(query)

    suspend fun connectStream(query:String) =
        twitterRepository.connectStreamAndUpdate(query)

    suspend fun disconnectStream() {
        twitterRepository.disconnectStream()
    }

}