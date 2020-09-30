package com.miguelete.twitterpins.di

import com.miguelete.twitterpins.data.server.TwitterDB
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "https://stream.twitter.com/1.1/"

    @Singleton
    @Provides
    fun twitterDBProvider(@Named("baseUrl") baseUrl: String) = TwitterDB(baseUrl)
}