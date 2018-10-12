package me.hackathon.monicershopsapp.ui.scanUtil

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.vision.MultiProcessor
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_scan.flash
import kotlinx.android.synthetic.main.activity_scan.preview
import me.hackathon.monicershopsapp.R
import me.hackathon.monicershopsapp.ui.summary.PaySummaryActivity
import java.io.IOException

class ScanActivity : DaggerAppCompatActivity(), BarcodeTracker.BarcodeGraphicTrackerCallback {

    var mCameraSource: CameraSource? = null
    private var isFlasOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_scan)

        val autoFocus = true
        val useFlash = false

        val rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash)
        } else {
            requestCameraPermission()
        }
    }

    fun onScanClick(view: View){
        try {
            isFlasOn = !isFlasOn
            mCameraSource?.setFlashMode(if (isFlasOn) Camera.Parameters.FLASH_MODE_TORCH else Camera.Parameters.FLASH_MODE_OFF)
            flash.setImageResource(
                    if (isFlasOn) R.drawable.ic_flash_on else R.drawable.ic_flash_off
            )
        } catch (e: Exception) {

        }
    }

    private fun createCameraSource(
            autoFocus: Boolean,
            useFlash: Boolean
    ) {
        val context = applicationContext

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        val barcodeDetector = BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build()
        val barcodeFactory = BarcodeTrackerFactory(this)
        barcodeDetector.setProcessor(MultiProcessor.Builder<Barcode>(barcodeFactory).build())

        if (!barcodeDetector.isOperational) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            val lowstorageFilter = IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW)
            val hasLowStorage = registerReceiver(null, lowstorageFilter) != null

//            if (hasLowStorage) {
//                Toast.makeText(this, getString(R.string.not_enough_memory), Toast.LENGTH_LONG)
//                        .show()
//            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)

        var builder = CameraSource.Builder(applicationContext, barcodeDetector)
                .setFacing(
                        CameraSource.CAMERA_FACING_BACK
                )
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f)

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    if (autoFocus) Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE else null
            )
        }

        mCameraSource = builder.setFlashMode(if (useFlash) Camera.Parameters.FLASH_MODE_TORCH else null)
                .build()
    }

    val RC_HANDLE_CAMERA_PERM = 2
    val RC_HANDLE_GMS = 9001

    private fun requestCameraPermission() {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // we have permission, so create the camerasource
            val autoFocus = true
            val useFlash = false
            createCameraSource(autoFocus, useFlash)
            return
        }

        val listener = { dialog: DialogInterface, id: Int  -> finish() }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.error))
                .setMessage(getString(R.string.allow_camera_error))
                .setPositiveButton("OK", listener)
                .show()
    }

    // Restarts the camera
    override fun onResume() {
        super.onResume()
        startCameraSource()
    }

    // Stops the camera
     override fun onPause() {
        super.onPause()
        if (preview != null) {
            preview.stop()
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
     override fun onDestroy() {
        super.onDestroy()
        if (preview != null) {
            preview.release()
        }
    }

    @Throws(SecurityException::class)
    private fun startCameraSource() {
        // check that the device has play services available.
        val code = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(applicationContext)
        if (code != ConnectionResult.SUCCESS) {
            val dlg = GoogleApiAvailability.getInstance()
                    .getErrorDialog(this, code, RC_HANDLE_GMS)
            dlg.show()
        }

        if (mCameraSource != null) {
            try {
                preview.start(mCameraSource)
            } catch (e: IOException) {
                mCameraSource?.release()
            }
        }
    }

    override fun onDetectedQrCode(barcode: Barcode) {
//        if (barcode.displayValue.length != 32) toast("Wrong code")
//        else {
//            startPaymentActivity(barcode.displayValue)
//            finish()
//        }

        startActivity(Intent(this, PaySummaryActivity::class.java).apply {
            putExtra(PaySummaryActivity.EXTRA_BILL_AMOUNT, intent.getStringExtra(EXTRA_BILL_AMOUNT))
            putExtra(PaySummaryActivity.EXTRA_CODE, barcode.displayValue)
        })
        finish()
    }

    companion object {
      const val EXTRA_BILL_AMOUNT = "bill_amount"
    }
}

fun Context.scanActivityIntent() = Intent(this, ScanActivity::class.java)
fun Context.startScanActivity() = startActivity(scanActivityIntent())