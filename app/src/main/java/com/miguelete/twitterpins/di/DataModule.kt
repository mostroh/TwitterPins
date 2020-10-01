package com.miguelete.twitterpins.di

import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.LocationRepository
import com.miguelete.data.repository.PermissionChecker
import com.miguelete.data.repository.TwitterRepository
import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(locationDataSource, permissionChecker)


    @Provides
    fun twitterRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        locationRepository: LocationRepository
    ) = TwitterRepository(localDataSource, remoteDataSource, locationRepository)
}