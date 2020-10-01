package com.miguelete.twitterpins.di

import android.app.Application
import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.PermissionChecker
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.data.AndroidPermissionChecker
import com.miguelete.twitterpins.data.PlayServicesLocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.api_key)

    @Provides
    @Singleton
    @Named("secretKey")
    fun apiSecretProvider(app: Application): String = app.getString(R.string.api_secret)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)
}