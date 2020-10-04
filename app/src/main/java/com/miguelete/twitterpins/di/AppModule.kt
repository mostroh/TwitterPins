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
import com.miguelete.twitterpins.data.server.OauthKeys
import com.miguelete.twitterpins.data.server.TwitterDbDataSource
import com.miguelete.twitterpins.data.server.TwitterStreamConnector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {


    @Provides
    @Singleton
    fun oauthKeysProvider(app: Application): OauthKeys = OauthKeys(
        app.getString(R.string.api_key),
        app.getString(R.string.api_secret),
        app.getString(R.string.access_token),
        app.getString(R.string.access_secret)
    )

//    @Provides
//    @Singleton
//    fun oauthKeysProvider(app: Application): OauthKeys = OauthKeys(
//        app.getString(R.string.api_key),
//        app.getString(R.string.api_secret)
//    )

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
    fun remoteDataSourceProvider(
        twitterStreamConnector: TwitterStreamConnector) : RemoteDataSource = TwitterDbDataSource( twitterStreamConnector)


}