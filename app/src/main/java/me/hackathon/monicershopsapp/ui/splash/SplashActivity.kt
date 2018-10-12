package me.hackathon.monicershopsapp.ui.splash

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.hackathon.monicershopsapp.R
import me.hackathon.monicershopsapp.ui.login.LoginActivity

class SplashActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    startActivity(Intent(this, LoginActivity::class.java))
    supportFinishAfterTransition()
  }
}
