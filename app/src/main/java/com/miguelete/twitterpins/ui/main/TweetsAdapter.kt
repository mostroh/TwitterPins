package com.miguelete.twitterpins.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguelete.domain.Tweet
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.databinding.ViewTweetBinding
import com.miguelete.twitterpins.ui.common.bindingInflate
import com.miguelete.twitterpins.ui.common.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_tweet.view.*


class TweetsAdapter() :
    ListAdapter<Tweet, TweetsAdapter.TweetViewHolder>(TweetDataAdapterListDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder =
        TweetViewHolder(parent.inflate(R.layout.view_tweet))


    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class TweetDataAdapterListDiff : DiffUtil.ItemCallback<Tweet>() {

        override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
            return oldItem == newItem
        }
    }

    inner class TweetViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(tweet: Tweet) {

            with(containerView) {
                tvTweetText.text = tweet.text
                tvTweetUser.text = tweet.user

            }
        }
    }
}