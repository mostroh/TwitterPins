package com.miguelete.twitterpins

import com.miguelete.data.LocationDataSource
import com.miguelete.data.repository.PermissionChecker
import com.miguelete.data.source.LocalDataSource
import com.miguelete.data.source.RemoteDataSource
import com.miguelete.domain.LatLong
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.di.DataModule
import com.miguelete.twitterpins.di.TwitterPinsComponent
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestAppModule::class,
    DataModule::class
])
interface TestComponent: TwitterPinsComponent {

    val remoteDataSource: RemoteDataSource

    @Component.Factory
    interface FactoryTest {
        fun create(): TestComponent
    }
}

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun remoteDataSourceProvider(): RemoteDataSource = FakeRemoteDataSource()

    @Provides
    @Singleton
    fun localDataSourceProvider(): LocalDataSource = FakeLocalDataSource()

    @Provides
    @Singleton
    fun locationDataSourceProvider(): LocationDataSource = FakeLocationDataSource()

    @Provides
    @Singleton
    fun permissionCheckerProvider(): PermissionChecker = FakePermissionChecker()

}


class FakeRemoteDataSource : RemoteDataSource {

    var tweet = defaultFakeTweet

    override suspend fun getStreamTweet(query: String): Flow<Tweet> = flow{emit(tweet)}

    override suspend fun disconnectStream() {}
}

class FakeLocalDataSource : LocalDataSource {

    var tweets : MutableList<Tweet> = mutableListOf()

    override suspend fun saveTweets(tweets: List<Tweet>) {
        this.tweets.addAll(tweets)
    }

    override suspend fun saveTweet(tweet: Tweet) {
        this.tweets.add(tweet)
    }
    override fun getTweets(): Flow<List<Tweet>> {
        return flow { tweets }
    }

}

class FakeLocationDataSource : LocationDataSource {

    var latLong = LatLong(40.416775, -3.703790)

    override suspend fun findLastLocation(): LatLong? = latLong
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
            permissionGranted
}

val defaultFakeTweet = mockedTweet