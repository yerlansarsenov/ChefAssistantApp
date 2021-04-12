package kz.spoonacular.chefassistant.customViews.custom_snackbar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import kz.spoonacular.chefassistant.R

class CustomSnackbar(
    parent: ViewGroup,
    content: CustomSnackbarViewRed
): BaseTransientBottomBar<CustomSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
                ContextCompat.getColor(
                        view.context,
                        android.R.color.transparent
                )
        )
    }

    companion object {
        fun instance(viewGroup: ViewGroup, text: String): CustomSnackbar {
            val layout = R.layout.layout_custom_snackbar
            val snackbarView = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(layout, viewGroup, false) as CustomSnackbarViewRed
            val textView = snackbarView.findViewById<TextView>(R.id.custom_toast_text)
            textView.text = text
            return CustomSnackbar(viewGroup, snackbarView)
        }
    }

}