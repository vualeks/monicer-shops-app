package me.hackathon.monicershopsapp.ui.splash

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.hackathon.monicershopsapp.R

class SplashActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
  }
}
