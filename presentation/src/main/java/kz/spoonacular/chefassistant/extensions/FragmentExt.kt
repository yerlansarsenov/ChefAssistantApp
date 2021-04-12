package kz.spoonacular.chefassistant.extensions

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import kz.spoonacular.chefassistant.customViews.CustomLoadingLayout
import kz.spoonacular.chefassistant.customViews.custom_snackbar.CustomSnackbar

fun <T : Fragment> T.showProcessLoading(): AlertDialog? {
    val loadingLayout = CustomLoadingLayout(activity!!)

    val builder = AlertDialog.Builder(activity)
    builder.setCancelable(false)
    builder.setView(loadingLayout)

    return builder.create()
}

fun <T : Fragment> T.showToast(text: String) {
    // Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    activity!!.showToast(text)
}

/**
 * intentFor
 */

inline fun <reified T : AppCompatActivity> Fragment.intentFor(vararg pairs: Pair<String, Any?>): Intent {
    val intent = Intent(activity, T::class.java)
    pairs.forEach {
        intent.putExtraOfAny(it.first, it.second)
    }
    return intent
}

inline fun <reified T> Fragment.lazyArg(key: String): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val value = requireArguments().get(key)
        return@lazy value as T
    }
}