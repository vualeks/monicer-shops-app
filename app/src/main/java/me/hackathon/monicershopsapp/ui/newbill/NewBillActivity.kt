package me.hackathon.monicershopsapp.ui.newbill

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import me.hackathon.monicershopsapp.R
import me.hackathon.monicershopsapp.databinding.ActivityNewBillBinding
import me.hackathon.monicershopsapp.util.Constants.CURRENT_USER
import me.hackathon.monicershopsapp.util.CustomViewModelFactory
import javax.inject.Inject
import android.opengl.ETC1.getHeight
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import me.hackathon.monicershopsapp.ui.scanUtil.ScanActivity

class NewBillActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var viewModelFactory: CustomViewModelFactory
  lateinit var viewModel: NewBillViewModel
  lateinit var binding: ActivityNewBillBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_new_bill)
    viewModel = ViewModelProviders.of(this, viewModelFactory)[NewBillViewModel::class.java]
    viewModel.shopTitle.set(CURRENT_USER.shop.name)
    binding.viewModel = viewModel

    binding.root.viewTreeObserver
        .addOnGlobalLayoutListener {
          val r = Rect()
          binding.root.getWindowVisibleDisplayFrame(r)
          val screenHeight = binding.root.rootView
              .height

          val keypadHeight = screenHeight - r.bottom


          if (keypadHeight > screenHeight * 0.15) {
            binding.shopTitle.visibility = View.GONE
          } else {
            binding.shopTitle.visibility = View.VISIBLE
          }
        }
    binding.cancel.setOnClickListener {
      finish()
    }
    binding.confirmButton.setOnClickListener {
      startActivity(Intent(this, ScanActivity::class.java).apply {
        putExtra(ScanActivity.EXTRA_BILL_AMOUNT, viewModel.billAmount.get())
      })
      finish()
    }
  }

}
