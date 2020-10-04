package com.miguelete.twitterpins.ui.main

import androidx.lifecycle.*
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.ui.common.Event
import com.miguelete.usecases.GetRecentTweets
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val getRecentTweets: GetRecentTweets) : ViewModel() {


    companion object{
        private const val SECONDS_TO_CLEAR :Long = 60
    }

    private val _spinner = MutableLiveData<Boolean>()
    val spinner : LiveData<Boolean> get() = _spinner

    private val _error = MutableLiveData<String?>()
    val error : LiveData<String?> get() = _error

    @ExperimentalCoroutinesApi
    private val searchChanel = ConflatedBroadcastChannel<String>()

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
            getRecentTweets.getTweets(query)
                .onStart {
                    _spinner.value = true
                }
                .catch { trowable ->
                    showError("Error ${trowable.message}")
                }
                .collect { tweets ->
                    _spinner.value = false
                    _tweets.value = tweets.filter {twt ->
                        val current= System.currentTimeMillis()
                        val passedSeconds = passedSeconds(twt.insertedAt, current)
                        passedSeconds <= SECONDS_TO_CLEAR
                    }
                }
        }
    }

    private fun passedSeconds(start: Long, end: Long): Long {
        val diff =(end - start)
        val passed = (diff /1000)
        return passed
    }


    @ExperimentalCoroutinesApi
    fun searchQuery(query: String) {
        searchChanel.offer(query)
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

