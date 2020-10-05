package com.miguelete.twitterpins.ui.main

import com.miguelete.data.repository.TwitterRepository
import com.miguelete.usecases.GetRecentTweets
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(getRecentTweets: GetRecentTweets) = MainViewModel(getRecentTweets)

    @Provides
    fun getRecentTweetsProvider(twitterRepository: TwitterRepository) =
        GetRecentTweets(twitterRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {

    val mainViewModel: MainViewModel
}