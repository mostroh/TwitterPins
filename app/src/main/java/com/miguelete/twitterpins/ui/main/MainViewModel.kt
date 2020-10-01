package com.miguelete.twitterpins.ui.main

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.ui.common.Event
import com.miguelete.usecases.GetRecentTweets
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
class MainViewModel(private val getRecentTweets: GetRecentTweets) : ViewModel() {

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
            getRecentTweets.invoke(query)
                .onStart { _spinner.value =  true }
                .catch { exception ->
                    _spinner.value = false
                    _error.value = exception.message
                }
                .collect { tweetItems ->
                    _spinner.value = false
                    _tweets.value = tweetItems
            }

        }
    }

    fun onSearchQuery(query: String) {
        _queryTweet.value = Event(query)
    }
}

