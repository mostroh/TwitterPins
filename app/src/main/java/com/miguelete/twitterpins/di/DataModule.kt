package com.miguelete.twitterpins.di

import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.LocationRepository
import com.miguelete.data.repository.PermissionChecker
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun locationRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = LocationRepository(locationDataSource, permissionChecker)
}