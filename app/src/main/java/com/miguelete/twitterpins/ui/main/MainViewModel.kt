package com.miguelete.twitterpins.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.ui.common.Event
import com.miguelete.usecases.GetRecentTweets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(private val getRecentTweets: GetRecentTweets) : ViewModel() {


    companion object{
        private const val MILLISECONDS_TO_CLEAR :Long = 5000
    }

    private val _spinner = MutableLiveData<Boolean>()
    val spinner : LiveData<Boolean> get() = _spinner

    private val _error = MutableLiveData<String?>()
    val error : LiveData<String?> get() = _error

    private val _queryTweet = MutableLiveData<Event<String>>()
    val queryTweet: LiveData<Event<String>> get() = _queryTweet

    private val _tweets = MutableLiveData<List<Tweet>>()
    val tweets : LiveData<List<Tweet>> get() = _tweets

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    init {
        _requestLocationPermission.value = Event(Unit)
    }

    fun loadRecentTweets(query: String) {
        viewModelScope.launch {
            getRecentTweets.connectStream(query)
                .onStart {

                }
                .catch { trowable ->
                    showError("Error ${trowable.message}")
                }
                .collect{ tweets ->
                    _tweets.value = tweets
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
        _queryTweet.value = Event(query)
    }

    fun stopFetchingTweets() {
        viewModelScope.launch {
            getRecentTweets.disconnectStream()
        }
    }
}

