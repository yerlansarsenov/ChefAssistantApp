package kz.spoonacular.chefassistant.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.setImageWithUrl(url: String) {
    Picasso.get()
        .load(url)
        .into(this)
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

fun AppCompatImageView.setImageWithUrl(url: String) {
    if (url.isEmpty()) {
        return
    }
    Picasso.get()
        .load(url)
        .into(this)
}

fun AppCompatImageView.setImageWithUrlAndFit(url: String, width: Int = 50, height: Int = 50) {
    if (url.isEmpty()) {
        return
    }
    Picasso.get()
        .load(url)
        .resize(width, height)
        .into(this)
}

fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun View.hideMe() {
    this.visibility = View.GONE
}
