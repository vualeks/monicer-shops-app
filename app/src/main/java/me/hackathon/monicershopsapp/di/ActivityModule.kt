package me.hackathon.monicershopsapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.hackathon.monicershopsapp.ui.login.LoginActivity
import me.hackathon.monicershopsapp.ui.main.MainActivity
import me.hackathon.monicershopsapp.ui.newbill.NewBillActivity
import me.hackathon.monicershopsapp.ui.scanUtil.ScanActivity
import me.hackathon.monicershopsapp.ui.splash.SplashActivity
import me.hackathon.monicershopsapp.ui.summary.PaySummaryActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindNewBillActivity(): NewBillActivity

    @ContributesAndroidInjector
    abstract fun bindScanActivity(): ScanActivity

    @ContributesAndroidInjector
    abstract fun bindPaySummaryActivity(): PaySummaryActivity
}