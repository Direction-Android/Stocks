package uz.azim.stocks.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

fun Fragment.navigate(@IdRes currentFragmentId: Int, @IdRes actionId: Int) {
    val navController = findNavController()
    if (navController.currentDestination?.id == currentFragmentId)
        navController.navigate(actionId)
}

fun Fragment.navigate(@IdRes currentFragmentId: Int, navAction: NavDirections) {
    val navController = findNavController()
    if (navController.currentDestination?.id == currentFragmentId)
        navController.navigate(navAction)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}


fun Int.toDp(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun ViewGroup.inflate(@LayoutRes layoutResId: Int): View {
    return LayoutInflater.from(context).inflate(layoutResId, this, false)
}

fun ImageView.setStockLogo(symbol: String, errorImg: Drawable?) {
    Glide.with(context)
            .load("https://finnhub.io/api/logo?symbol=$symbol")
            .error(errorImg)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(this)
}

fun ImageView.setImageRemote(imgUrl: String, errorImg: Drawable?) {
    Glide.with(context)
            .load(imgUrl)
            .error(errorImg)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(this)
}

fun Any?.isNull() = this == null

fun Number.formatMoney(): String {
    return DecimalFormat("$###,###,###,###.###").format(this).replace(",", " ")
}

fun Number.decimalsAfter(amount: Int): String {
    return String.format("%.${amount}f", this)
}

fun Any.log(text: String) {
    Log.d("MyLog", text)
}

fun View.showSnackbar(text: String) {
    Snackbar.make(this, text, Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show()
}

/**
 * Extra Functions
 */


fun getDrawable(context: Context, @DrawableRes res: Int): Drawable? {
    return ContextCompat.getDrawable(context, res)
}

fun getColor(context: Context, @ColorRes res: Int): Int {
    return ContextCompat.getColor(context, res)
}
