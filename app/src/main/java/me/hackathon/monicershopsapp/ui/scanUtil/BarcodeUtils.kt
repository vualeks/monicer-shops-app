package me.hackathon.monicershopsapp.ui.scanUtil

import android.content.Context
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.Tracker
import com.google.android.gms.vision.barcode.Barcode

open class BarcodeTrackerFactory internal constructor(private val mContext: Context) : MultiProcessor.Factory<Barcode> {

  override fun create(barcode: Barcode): Tracker<Barcode> {
    return BarcodeTracker(mContext)
  }
}

class BarcodeTracker internal constructor(listener: Context) : Tracker<Barcode>() {
  private val mListener: BarcodeGraphicTrackerCallback

  interface BarcodeGraphicTrackerCallback {
    fun onDetectedQrCode(barcode: Barcode)
  }

  init {
    mListener = listener as BarcodeGraphicTrackerCallback
  }

  override fun onNewItem(
    id: Int,
    item: Barcode?
  ) {
    if (item!!.displayValue != null) {
      mListener.onDetectedQrCode(item)
    }
  }
}
