package me.hackathon.monicershopsapp.ui.summary

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_pay_summary.cancel_button
import kotlinx.android.synthetic.main.activity_pay_summary.progressBar
import kotlinx.android.synthetic.main.activity_pay_summary.success_image
import kotlinx.android.synthetic.main.activity_pay_summary.summary_description
import me.hackathon.monicershopsapp.R
import me.hackathon.monicershopsapp.network.ApiService
import me.hackathon.monicershopsapp.vo.PayRequest
import javax.inject.Inject

class PaySummaryActivity : DaggerAppCompatActivity() {

  @Inject
  lateinit var api: ApiService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pay_summary)

    val billAmount = intent.getStringExtra(EXTRA_BILL_AMOUNT)
    val code = intent.getStringExtra(EXTRA_CODE)

    api.pay(PayRequest(code, billAmount)).observe(this, Observer {
      if(it.isSuccessful) {
        cancel_button.visibility = View.VISIBLE
        success_image.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
        summary_description.apply {
          text = getString(R.string.payment_successful)
          textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 7f, resources.displayMetrics)
          setTextColor(ContextCompat.getColor(this@PaySummaryActivity, R.color.colorTextPrimary))
        }
      } else {
        // TODO: Add error message
        Log.d("Main", "Doslo je do greske")
      }
    })

    cancel_button.setOnClickListener { finish() }
  }

  companion object {
    const val EXTRA_BILL_AMOUNT = "bill_amount"
    const val EXTRA_CODE = "code"
  }

}