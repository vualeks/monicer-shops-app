package me.hackathon.monicershopsapp.ui.login

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.hackathon.monicershopsapp.R

class LoginActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}
