package com.miguelete.twitterpins.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.ui.common.loadUrlAndCircle
import com.miguelete.twitterpins.ui.model.Tweet
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val TWEET = "DetailActivity:tweet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tweet = intent.getParcelableExtra<Tweet>(TWEET)
        tweet?.let {
            tvUser.text = tweet.user
            tvCreatedAt.text = tweet.createdAt
            tvText.text = tweet.text
            ivUserImage.loadUrlAndCircle(tweet.user_image)
        }
    }
}