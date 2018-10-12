package me.hackathon.monicershopsapp.ui.newbill

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import me.hackathon.monicershopsapp.network.ApiService
import me.hackathon.monicershopsapp.util.Constants.CURRENT_USER
import me.hackathon.monicershopsapp.vo.AuthenticationRequest
import me.hackathon.monicershopsapp.vo.PayRequest
import javax.inject.Inject

class NewBillViewModel @Inject constructor(private val api: ApiService): ViewModel() {

  val billAmount = ObservableField<String>()
  val shopTitle = ObservableField<String>()

  fun pay() = api.pay(PayRequest(CURRENT_USER.payCode, billAmount.get()!!))

}