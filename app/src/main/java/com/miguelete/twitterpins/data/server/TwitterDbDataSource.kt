package com.miguelete.twitterpins.data.server

import android.util.Log
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.data.randomLatLong
import com.miguelete.twitterpins.data.toDomainTweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import twitter4j.StallWarning
import twitter4j.Status
import twitter4j.StatusDeletionNotice
import twitter4j.StatusListener
import java.util.*

class TwitterDbDataSource(private val twitterStreamConnector: TwitterStreamConnector) : RemoteDataSource {


    override suspend fun getStreamAsFlow(query: String) : Flow<List<Tweet>> = fetchFakeFlow(query)
//    {
//        Log.d("TweetStream", "from repository query launched $query")
//        return getStreamTweet(query)
//    }

    private fun fetchFakeFlow(query: String) : Flow<List<Tweet>> = flow {
        var count = 0L
        while (true) {
            delay(2000)
            emit(listOf(Tweet(count,
                "This is tweet$count",
                "23:00 03/10/2020",
                29,
                randomLatLong(),
                "Miguel$count",
                "https://pbs.twimg.com/media/ET0IQ3pX0AE_QrO.jpg",
                System.currentTimeMillis()
            )))
            count++
        }
    }

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
//                        if (it.geoLocation != null) {
//                            Log.d("TweetStream", "onStatus ${it.text} \n  latlong: ${it.geoLocation.latitude},${it.geoLocation.longitude} ")
//                            offer(listOf(it.toDomainTweet()))
//                        }
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


    override suspend fun disconnectStream() {
        twitterStreamConnector.clearStream()
    }


}





