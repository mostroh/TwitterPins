package com.miguelete.twitterpins.di

import com.miguelete.twitterpins.data.server.TwitterDB
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServerModule {

    @Singleton
    @Provides
    @Named("baseUrl")
    fun baseUrlProvider() = "https://stream.twitter.com/1.1/"


    //TODO crear los headers debidamente
    @Singleton
    @Provides
    fun okHttpClientProvider(
        @Named("apiKey") apiKey: String,
        @Named("secretKey") secretKey: String) =
        OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("X-App-Version", "1.23")
                    builder.header("X-Platform", "Android")
                    builder.header("X-Auth-Token", "sgsrager32524542afg3423")
                    return@Interceptor chain.proceed(builder.build())
            }
        )
    }.build()

    @Singleton
    @Provides
    fun twitterDBProvider(
        @Named("baseUrl") baseUrl: String,
        okHttpClient : OkHttpClient) = TwitterDB(baseUrl, okHttpClient)
}