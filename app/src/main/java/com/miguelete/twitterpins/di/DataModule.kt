package com.miguelete.twitterpins.di

import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.LocationRepository
import com.miguelete.data.repository.PermissionChecker
import com.miguelete.data.repository.TwitterRepository
import com.miguelete.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.crypto.SecretKey
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(locationDataSource, permissionChecker)


    @Provides
    fun twitterRepositoryProvider(
        remoteDataSource: RemoteDataSource,
        locationRepository: LocationRepository,
        @Named("apiKey") apiKey: String,
        @Named("secretKey") secretKey: String
    ) = TwitterRepository(remoteDataSource, locationRepository, apiKey, secretKey)
}