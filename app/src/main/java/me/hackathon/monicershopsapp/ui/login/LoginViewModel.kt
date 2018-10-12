package me.hackathon.monicershopsapp.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.hackathon.monicershopsapp.network.ApiService
import me.hackathon.monicershopsapp.network.TokenHeaderInterceptor
import me.hackathon.monicershopsapp.util.Constants.CURRENT_USER
import me.hackathon.monicershopsapp.vo.AuthenticationRequest
import me.hackathon.monicershopsapp.vo.AuthenticationResponse
import javax.inject.Inject

class LoginViewModel @Inject constructor(
  private val api: ApiService,
  private val tokenHeaderInterceptor: TokenHeaderInterceptor
) : ViewModel() {

  var email = ObservableField("")
  var password = ObservableField("")

  fun authenticate() = api.authenticate(AuthenticationRequest(email.get()!!, password.get()!!))

  fun initUser(authResponse: AuthenticationResponse) {
    tokenHeaderInterceptor.token = authResponse.accessToken
    CURRENT_USER = authResponse.user
  }
}