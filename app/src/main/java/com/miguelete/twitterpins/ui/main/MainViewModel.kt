package com.miguelete.twitterpins.ui.main

import androidx.lifecycle.*
import com.miguelete.twitterpins.data.toUiTweet

import com.miguelete.twitterpins.ui.common.Event
import com.miguelete.twitterpins.ui.model.Tweet

import com.miguelete.usecases.GetRecentTweets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val getRecentTweets: GetRecentTweets) : ViewModel() {

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean> get() = _spinner

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _queryTweet = MutableLiveData<Event<String>>()
    val queryTweet: LiveData<Event<String>> get() = _queryTweet

    private val _lastTweet = MutableLiveData<Tweet>()
    val lastTweet: LiveData<Tweet> get() = _lastTweet

    private val _navigateToDetail = MutableLiveData<Event<Tweet>>()
    val navigateToDetail: LiveData<Event<Tweet>> get() = _navigateToDetail

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    init {
        _requestLocationPermission.value = Event(Unit)
    }

    @ExperimentalCoroutinesApi
    fun loadRecentTweets(query: String) {
        viewModelScope.launch {
            getRecentTweets.observeTweets(query)
                .catch { throwable ->
                    showError("Error ${throwable.message}")
                }
                .collect { tweet ->
                    _spinner.value = false
                    _lastTweet.value = tweet.toUiTweet()
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            getRecentTweets.disconnectStream()
        }
    }

    private fun showError(errorMsg: String) {
        _spinner.value = false
        _error.value = errorMsg
    }

    fun onSearchQuery(query: String) {
        _spinner.value = true
        _queryTweet.value = Event(query)
    }

    fun onTweetClicked(tweet: Tweet) {
        _navigateToDetail.value = Event(tweet)
    }

    fun stopFetchingTweets() {
        viewModelScope.launch {
            getRecentTweets.disconnectStream()
        }
    }
}

