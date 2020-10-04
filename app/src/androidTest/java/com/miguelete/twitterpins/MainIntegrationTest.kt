package com.miguelete.twitterpins

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.miguelete.twitterpins.ui.main.MainActivityModule

import com.miguelete.twitterpins.ui.main.MainViewModel
import com.miguelete.twitterpins.ui.model.Tweet
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val observer: Observer<Tweet> = mock()
    private val component: TestComponent = DaggerTestComponent.factory().create()
    private lateinit var remoteDataSource: FakeRemoteDataSource
    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        vm = component.plus(MainActivityModule()).mainViewModel
        remoteDataSource = component.remoteDataSource as FakeRemoteDataSource
        remoteDataSource.tweet = defaultFakeTweet
    }

    @ExperimentalCoroutinesApi
    @Test
    fun dataIsLoadedFromRemoteDataSource() = coroutinesTestRule.testDispatcher.runBlockingTest {

    }

}