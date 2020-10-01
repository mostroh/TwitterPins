package com.miguelete.twitterpins.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.databinding.ViewTweetBinding
import com.miguelete.twitterpins.ui.common.bindingInflate


class TweetsAdapter() :
    RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    var tweets: List<Tweet> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.view_tweet, false))

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.dataBinding.tweet = tweet
    }

    class ViewHolder(val dataBinding: ViewTweetBinding) : RecyclerView.ViewHolder(dataBinding.root)
}