package me.hackathon.monicershopsapp.ui.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.hackathon.monicershopsapp.R

class MainActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

  }
}
