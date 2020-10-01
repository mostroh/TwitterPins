package com.miguelete.twitterpins.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguelete.domain.Tweet

@BindingAdapter("items")
fun RecyclerView.setItems(tweets: List<Tweet>?) {
    (adapter as? TweetsAdapter)?.let {
        it.tweets = tweets ?: emptyList()
    }
}