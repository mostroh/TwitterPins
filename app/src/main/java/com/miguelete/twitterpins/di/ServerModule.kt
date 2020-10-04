package com.miguelete.twitterpins.di

import com.miguelete.twitterpins.data.server.OauthKeys
import com.miguelete.twitterpins.data.server.TwitterStreamConnector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    fun twitterStreamConnectorProvider(
        oauthKeys: OauthKeys
    ) = TwitterStreamConnector(oauthKeys)

}