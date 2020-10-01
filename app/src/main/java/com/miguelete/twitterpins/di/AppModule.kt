package com.miguelete.twitterpins.di

import android.app.Application
import androidx.room.Room
import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.PermissionChecker
import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.data.AndroidPermissionChecker
import com.miguelete.twitterpins.data.PlayServicesLocationDataSource
import com.miguelete.twitterpins.data.db.RoomDataSource
import com.miguelete.twitterpins.data.db.TweetDatabase
import com.miguelete.twitterpins.data.server.TwitterDB
import com.miguelete.twitterpins.data.server.TwitterDbDataSource
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
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        TweetDatabase::class.java,
        "twitter-db"
    ).build()

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun localDataSourceProvider(db: TweetDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(twitterDB: TwitterDB,
                                 @Named("apiKey") apiKey: String,
                                 @Named("secretKey") secretKey: String) : RemoteDataSource = TwitterDbDataSource(twitterDB)


}