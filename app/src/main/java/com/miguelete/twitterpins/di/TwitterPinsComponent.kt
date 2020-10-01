package com.miguelete.twitterpins.di

import android.app.Application
import com.miguelete.twitterpins.ui.main.MainActivityComponent
import com.miguelete.twitterpins.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ServerModule::class])
interface TwitterPinsComponent {

    fun plus(module: MainActivityModule): MainActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): TwitterPinsComponent
    }
}