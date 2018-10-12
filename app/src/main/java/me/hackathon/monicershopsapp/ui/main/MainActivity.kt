package me.hackathon.monicershopsapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.new_bill_button
import me.hackathon.monicershopsapp.R
import me.hackathon.monicershopsapp.databinding.ActivityMainBinding
import me.hackathon.monicershopsapp.ui.newbill.NewBillActivity
import me.hackathon.monicershopsapp.util.Constants.CURRENT_USER
import me.hackathon.monicershopsapp.util.CustomViewModelFactory
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: CustomViewModelFactory
  lateinit var viewModel: MainViewModel
  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
    viewModel.shopTitle.set(CURRENT_USER.shop.name)
    binding.viewModel = viewModel

    new_bill_button.setOnClickListener {
      startActivity(Intent(this, NewBillActivity::class.java))
    }

  }
}
