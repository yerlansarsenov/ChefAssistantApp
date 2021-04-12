package kz.spoonacular.chefassistant.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.customViews.CustomMatrixView
import kz.spoonacular.chefassistant.customViews.custom_snackbar.CustomSnackbar

inline fun <reified T: AppCompatActivity> AppCompatActivity.intentFor(vararg pairs: Pair<String, Any?>): Intent {
    val intent = Intent(this, T::class.java)
    pairs.forEach {
        intent.putExtraOfAny(it.first, it.second)
    }
    return intent
}

fun <T: Activity> T.showToast(text: String) {
    //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    val snackBar = CustomSnackbar.instance(findViewById(android.R.id.content), text)
    // val view = snackBar.view
    // val params = view.layoutParams as FrameLayout.LayoutParams
    snackBar.apply {
        duration = BaseTransientBottomBar.LENGTH_LONG
        show()
    }
}

fun <T: Activity> T.customSnackbar(text: String): CustomSnackbar {
    return CustomSnackbar.instance(findViewById(android.R.id.content), text)
}

fun <T: Activity> T.showProcessLoading() : androidx.appcompat.app.AlertDialog {
    //val loadingLayout = CustomLoadingLayout(this)
    val inflater = this.layoutInflater
    val builder = androidx.appcompat.app.AlertDialog.Builder(this)
    builder.setCancelable(false)
    builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
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

inline fun <reified T> Activity.lazyArg(key: String) : Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val value = intent.extras?.get(key)
        return@lazy value as T
    }
}