package kz.forte.food.core.view.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.android.synthetic.main.custom_snackbar_green.view.*
import kz.forte.food.R

class CustomSnackbar(
        parent: ViewGroup,
        content: View,
        contentViewCallback: CustomContentViewCallback
): BaseTransientBottomBar<CustomSnackbar>(parent, content, contentViewCallback) {

    init {
        getView().setBackgroundColor(
                ContextCompat.getColor(
                        view.context,
                        android.R.color.transparent
                )
        )
    }

    companion object {
        const val SNACKBAR_BLUE = 0
        const val SNACKBAR_JAZZBERRY_JAM = 1
        const val SNACKBAR_SUCCESS = 2
        const val SNACKBAR_ERROR = 3

        fun instance(viewGroup: ViewGroup, text: String, color: Int): CustomSnackbar {
            val layout = when (color) {
                SNACKBAR_ERROR -> R.layout.layout_custom_snackbar_red
                SNACKBAR_SUCCESS -> R.layout.layout_custom_snackbar_green
                SNACKBAR_BLUE -> R.layout.layout_custom_snackbar_blue
                SNACKBAR_JAZZBERRY_JAM -> R.layout.layout_custom_snackbar_jazzberry_jam
                else -> R.layout.layout_custom_snackbar_jazzberry_jam
            }
            val snackbarView = LayoutInflater
                    .from(viewGroup.context)
                    .inflate(layout, viewGroup, false)
            snackbarView.custom_toast_text.text = text
            val contentViewCallback = CustomContentViewCallback()
            return CustomSnackbar(viewGroup, snackbarView, contentViewCallback)
        }
    }

}