package com.miguelete.twitterpins.data.server

data class OauthKeys(
    val consumerKey: String,
    val consumerSecret: String,
    val accessToken: String? = null,
    val accessSecret: String? = null
)