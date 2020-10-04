package com.miguelete.twitterpins.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.ui.common.inflate
import com.miguelete.twitterpins.ui.model.Tweet
import kotlinx.android.synthetic.main.view_tweet.view.*

class TwitterAdapter2(private val listener: (Tweet) -> Unit) :
    RecyclerView.Adapter<TwitterAdapter2.ViewHolder>() {

    var tweets: MutableList<Tweet> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_tweet, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tweets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.bind(tweet)
        holder.itemView.setOnClickListener { listener(tweet) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tweet: Tweet) {
            itemView.tvTweetText.text = tweet.text
            itemView.tvTweetUser.text = tweet.user
        }
    }
}