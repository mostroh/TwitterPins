package com.miguelete.twitterpins.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.ui.common.getViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var component: MainActivityComponent
    private val viewModel: MainViewModel by lazy { getViewModel{ component.mainViewModel }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}