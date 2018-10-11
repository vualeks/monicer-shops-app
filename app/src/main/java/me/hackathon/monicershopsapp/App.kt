package me.hackathon.monicershopsapp

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import me.hackathon.monicershopsapp.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out App> {
        return DaggerAppComponent.builder().application(this@App).build()
    }
}