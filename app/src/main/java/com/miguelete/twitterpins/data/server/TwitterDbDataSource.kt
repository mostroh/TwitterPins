package com.miguelete.twitterpins.data.server

import android.util.Log
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.toDomainTweet
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener

class TwitterDbDataSource(private val twitterStreamConnector: TwitterStreamConnector) : RemoteDataSource {


    override suspend fun getStreamAsFlow(query: String) : Flow<List<Tweet>> = getStreamTweet(query)

    private suspend fun getStreamTweet(query: String) : Flow<List<Tweet>> =
        callbackFlow {
            val callback = object : StatusListener {
                override fun onTrackLimitationNotice(numberOfLimitedStatuses: Int) {
                    Log.d("TweetStream", "onTrackLimitationNotice")
                    close()
                }

                override fun onStallWarning(warning: StallWarning?) {
                    Log.d("TweetStream", "onStallWarning ${warning?.message}")
                }

                override fun onException(ex: Exception?) {
                    Log.d("TweetStream", "onException ${ex?.message}")
                    close(ex)
                }

                override fun onDeletionNotice(statusDeletionNotice: StatusDeletionNotice?) {
                    Log.d("TweetStream", "onDeletionNotice")
                }

                override fun onStatus(status: Status?) {
                    status?.let {
                        offer(listOf(it.toDomainTweet()))
                    }
                }

                override fun onScrubGeo(userId: Long, upToStatusId: Long) {
                    Log.d("TweetStream", "onScrubGeo")
                }

            }

            twitterStreamConnector.connectToFilterStream(query, callback)
            awaitClose {
                twitterStreamConnector.clearStream()}
        }

//    private fun fetchFakeFlow(query: String) : Flow<List<Tweet>> = flow {
//        var count = 0L
//        while (true) {
//            delay(2000)
//            emit(listOf(Tweet(count,
//                "This is tweet$count $query",
//                "23:00 03/10/2020",
//                29,
//                randomLatLong(),
//                "User$count",
//                "https://pbs.twimg.com/media/ET0IQ3pX0AE_QrO.jpg",
//                System.currentTimeMillis()
//            )))
//            count++
//        }
//    }

    override suspend fun disconnectStream() {
        twitterStreamConnector.clearStream()
    }


}





