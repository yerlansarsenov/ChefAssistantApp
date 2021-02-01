package kz.spoonacular.chefassistant.extensions


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kz.spoonacular.chefassistant.customViews.CustomLoadingLayout
import kz.spoonacular.chefassistant.customViews.CustomMatrixView
import java.io.Serializable

/**
 * Created by Sarsenov Yerlan on 18.12.2020.
 */

/**
 * Extension functions for Fragment, ImageView, RatingBar, Context etc.
 */

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun <T: Fragment> T.showProcessLoading() : AlertDialog? {
    val loadingLayout = CustomLoadingLayout(context!!)

    val builder = AlertDialog.Builder(activity)
    builder.setCancelable(false)
    builder.setView(loadingLayout)

    return builder.create()
}

fun <T: Activity> T.showProcessLoading() : AlertDialog? {
    val loadingLayout = CustomLoadingLayout(this)

    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(loadingLayout)
    return builder.create()
}

fun Activity.showMatrixLoading(): AlertDialog? {
    val matrixView: CustomMatrixView = CustomMatrixView(this)
    val padding = 30
    matrixView.setPadding(padding, padding, padding, padding)
    matrixView.alpha = 0.9F
    val builder = AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(matrixView)
    return builder.create()
}

fun <T: Fragment> T.showToast(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun <T: Activity> T.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun ImageView.setImageWithUrl(url: String) {
    Picasso.get().load(url).into(this)
}

fun RatingBar.setImdbRating(rate: String) {
    try {
        val rateFloat = rate.toFloat()
        this.rating = rateFloat/2f
    } catch (e: Exception) {
        val tag = "RB_Exten_fun"
        Log.e(tag, "setImdbRating: ${e.message}")
    }
}

/**
 * intentFor
 */

inline fun <reified T: AppCompatActivity> AppCompatActivity.intentFor(vararg pairs: Pair<String, Any?>): Intent {
    val intent = Intent(this, T::class.java)
    pairs.forEach {
        intent.putExtraOfAny(it.first, it.second)
    }
    return intent
}

fun Intent.putExtraOfAny(key: String, value: Any?) {
    when (value) {
        is Boolean -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Short -> putExtra(key, value)
        is Bundle -> putExtra(key, value)
        is CharSequence -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
        is BooleanArray -> putExtra(key, value)
        is ByteArray -> putExtra(key, value)
        is CharArray -> putExtra(key, value)
        is DoubleArray -> putExtra(key, value)
        is FloatArray -> putExtra(key, value)
        is IntArray -> putExtra(key, value)
        is LongArray -> putExtra(key, value)
        is ShortArray -> putExtra(key, value)
        is Serializable -> putExtra(key, value)
    }
}

/**
 *  lazyArg
 *
 */

inline fun <reified T> Activity.lazyArg(key: String) : Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val value = intent.extras?.get(key)
        return@lazy value as T
    }
}

/**
 * lazyArg for Fragments
 */

inline fun <reified T> Fragment.lazyArg(key: String) : Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val value = requireArguments().get(key)
        return@lazy value as T
    }
}