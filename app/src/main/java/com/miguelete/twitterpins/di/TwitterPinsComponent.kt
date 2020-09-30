package com.miguelete.twitterpins.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ServerModule::class])
interface TwitterPinsComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): TwitterPinsComponent
    }
}