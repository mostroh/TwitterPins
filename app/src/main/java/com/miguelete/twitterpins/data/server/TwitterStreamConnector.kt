package com.miguelete.twitterpins.data.server

import android.util.Log
import twitter4j.*
import twitter4j.conf.ConfigurationBuilder

class TwitterStreamConnector(
    private val oauthKeys: OauthKeys) {

    private val configurationBuilder = ConfigurationBuilder()
    private val twitterStream : TwitterStream

    init {
        setupConfigurationBuilder()
        twitterStream = TwitterStreamFactory(configurationBuilder.build()).instance
    }

    private fun setupConfigurationBuilder(){
        configurationBuilder.setDebugEnabled(true)
        configurationBuilder.setOAuthConsumerKey(oauthKeys.consumerKey)
        configurationBuilder.setOAuthConsumerSecret(oauthKeys.consumerSecret)
        configurationBuilder.setOAuthAccessToken(oauthKeys.accessToken)
        configurationBuilder.setOAuthAccessTokenSecret(oauthKeys.accessSecret)
    }

    fun connectToFilterStream(query: String, statusListener: StatusListener) {
        twitterStream.addListener(statusListener)
        val filterQuery = FilterQuery()
        filterQuery.track(query)
        twitterStream.filter(filterQuery)
        Log.d("TweetStream", "from stream connector query launched $query")
    }

    fun clearStream(){
        twitterStream.clearListeners()
        twitterStream.cleanUp()
    }

}