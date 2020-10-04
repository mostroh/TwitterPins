package com.miguelete.twitterpins

import com.miguelete.twitterpins.data.server.Oauth1SigningInterceptor
import com.miguelete.twitterpins.data.server.OauthKeys
import org.junit.Test

import org.junit.Assert.*

/*
 * Copyright (C) 2015 Jake Wharton
 * Modified work Copyright 2019 Phil Olson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import okhttp3.FormBody
import okhttp3.Request
import org.junit.Assert.assertEquals
import java.io.IOException

/**
 * Used for building oauth header for TwitterApi
 * Taken from: https://gist.github.com/polson/227e1a039a09f2728163bf7235990178
 */
class Oauth1SigningInterceptorTest {

    //Callback
    private fun getOauthKeys() = OauthKeys(
        consumerKey = "xvz1evFS4wEEPTGEFPHBog",
        consumerSecret = "kAcSOqF21Fu85e7zjz7ZN2U4ZRhfV3WpwPAoE3Z7kBw",
        accessToken = "370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb",
        accessSecret = "LswwdoUaIvS8ltyTt5jkRh4J50vUPVVHtR2YPi5kE"
    )

    @Test
    @Throws(IOException::class)
    fun litmus() {
        val nonce = "kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg"
        val oauth1 = Oauth1SigningInterceptor(getOauthKeys(), nonce = nonce, timestamp = 1318622958)

        val requestBody = FormBody
            .Builder()
            .addEncoded("status", "Hello Ladies + Gentlemen, a signed OAuth request!")
            .build()

        val request = Request.Builder()
            .url("https://api.twitter.com/1/statuses/update.json?include_entities=true")
            .post(requestBody)
            .build()

        val signed = oauth1.signRequest(request)
        assertEquals(signed.header("Authorization"), "OAuth "
                + "oauth_consumer_key=\"xvz1evFS4wEEPTGEFPHBog\", "
                + "oauth_nonce=\"kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg\", "
                + "oauth_signature=\"tnnArxj06cWHq44gCs1OSKk%2FjLY%3D\", "
                + "oauth_signature_method=\"HMAC-SHA1\", "
                + "oauth_timestamp=\"1318622958\", "
                + "oauth_token=\"370773112-GmHxMAgYyLbNEtIKZeRNFsMKPR9EyMZeS9weJAEb\", "
                + "oauth_version=\"1.0\"")
    }
}