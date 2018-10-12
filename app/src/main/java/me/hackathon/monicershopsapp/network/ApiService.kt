package me.hackathon.monicershopsapp.network

import androidx.lifecycle.LiveData
import me.hackathon.monicershopsapp.vo.ApiResponse
import me.hackathon.monicershopsapp.vo.AuthenticationRequest
import me.hackathon.monicershopsapp.vo.AuthenticationResponse
import me.hackathon.monicershopsapp.vo.PayRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
  @POST("api/auth/token")
  fun authenticate(@Body authenticationRequest: AuthenticationRequest):
      LiveData<ApiResponse<AuthenticationResponse>>

  @POST("api/shop/initiate-payment")
  fun pay(@Body payRequest: PayRequest): LiveData<ApiResponse<Void>>
}