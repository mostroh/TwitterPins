package com.miguelete.twitterpins.ui.main

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miguelete.twitterpins.R
import com.miguelete.twitterpins.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import com.miguelete.twitterpins.ui.common.*
import com.miguelete.twitterpins.ui.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi


class MainActivity : AppCompatActivity() {

    private lateinit var component: MainActivityComponent
    private val viewModel: MainViewModel by lazy { getViewModel{ component.mainViewModel }}
    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = app.component.plus(MainActivityModule())

        //Some databinding example
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setUpListeners()

        loadMap()
        observeViewModels()
    }

    private fun setUpListeners() {
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

    @ExperimentalCoroutinesApi
    private fun observeViewModels(){
        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {}
        })

        viewModel.queryTweet.observe(this, EventObserver {
                searchCriteria -> viewModel.loadRecentTweets(searchCriteria)
        })

        viewModel.navigateToDetail.observe(this, EventObserver {
            startActivity<DetailActivity> {
                putExtra(DetailActivity.TWEET, it)
            }
        })
    }

    private fun loadMap(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.mapContainer, MapsFragment())
            .commitNow()
    }
}