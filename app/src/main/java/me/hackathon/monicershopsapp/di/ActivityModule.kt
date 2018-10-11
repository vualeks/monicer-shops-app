package me.hackathon.monicershopsapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hackathon.monicershopsapp.ui.login.LoginActivity
import me.hackathon.monicershopsapp.ui.main.MainActivity
import me.hackathon.monicershopsapp.ui.splash.SplashActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}