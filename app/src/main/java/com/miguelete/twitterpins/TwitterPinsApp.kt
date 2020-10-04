package com.miguelete.twitterpins

import android.app.Application
import com.facebook.stetho.Stetho
import com.miguelete.twitterpins.di.DaggerTwitterPinsComponent
import com.miguelete.twitterpins.di.TwitterPinsComponent

open class TwitterPinsApp : Application() {
    lateinit var  component : TwitterPinsComponent
    private set

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        component = initTwitterPinsComponent()
    }

    open fun initTwitterPinsComponent() = DaggerTwitterPinsComponent.factory().create(this)
}