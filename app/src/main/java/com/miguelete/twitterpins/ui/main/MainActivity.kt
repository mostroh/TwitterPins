package com.miguelete.twitterpins.ui.main

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.databinding.ActivityMainBinding
import com.miguelete.twitterpins.ui.common.PermissionRequester
import com.miguelete.twitterpins.ui.common.getViewModel
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import androidx.lifecycle.Observer
import com.miguelete.twitterpins.ui.common.EventObserver
import com.miguelete.twitterpins.ui.common.app
import com.miguelete.twitterpins.ui.common.startActivity
import com.miguelete.twitterpins.ui.detail.DetailActivity


class MainActivity : AppCompatActivity() {

    private lateinit var component: MainActivityComponent
    private val viewModel: MainViewModel by lazy { getViewModel{ component.mainViewModel }}
    private lateinit var adapter : TwitterAdapter2
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(MainActivityModule())

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = TwitterAdapter2(viewModel::onTweetClicked)
        recycler.adapter = adapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.onSearchQuery(query)}
                search.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

        observeViewModels()
    }

    override fun onStart() {
        super.onStart()
        with(search.query.toString()){
            if (!isNullOrEmpty()) viewModel.onSearchQuery(this)
            search.clearFocus()
        }

    }

    override fun onStop() {
        super.onStop()
        viewModel.stopFetchingTweets()
    }

    private fun observeViewModels(){
        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {}
        })

        viewModel.queryTweet.observe(this, EventObserver {
                searchCriteria -> viewModel.loadRecentTweets(searchCriteria)
        })

        viewModel.lastTweet.observe(this, Observer {
            adapter.tweets.add(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.navigateToDetail.observe(this, EventObserver {
            startActivity<DetailActivity> {
                putExtra(DetailActivity.TWEET, it)
            }
        })
    }
}