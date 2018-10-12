package me.hackathon.monicershopsapp.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import java.text.SimpleDateFormat
import java.util.*


fun Int.dpToPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return Math.round(px)
}

fun PopupWindow.dimBackground() {
    val container = this.contentView.rootView
    val context = this.contentView.context
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val lp = container.layoutParams as WindowManager.LayoutParams
    lp.flags = lp.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
    lp.dimAmount = 0.5f
    wm.updateViewLayout(container, lp)
}

/*fun AppCompatActivity.startFragment(fragment: DaggerFragment) {
    supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragment_container, fragment)
            .commit()
}*/

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Long.toDateString(): String {
    val dateFormat = SimpleDateFormat("MM/dd/yyyy\tHH:mm", Locale.getDefault())
    return dateFormat.format(this)
}

fun PopupWindow.setElementOnClick(id: Int, function: () -> Unit) {
    contentView.findViewById<View>(id).setOnClickListener {
        function()
    }
}

fun SearchView.getIcon(): ImageView {
    return findViewById(androidx.appcompat.R.id.search_mag_icon)

}

fun SearchView.getText(): TextView {
    return findViewById(androidx.appcompat.R.id.search_src_text)
}

fun Activity.screenWidth(): Int {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.widthPixels
}

fun Activity.screenHeight(): Int {
    val metrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels
}
