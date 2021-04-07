package kz.forte.food.core.view.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kz.forte.food.R

class CustomSnackbarViewRed @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle) {

    val layout: Int = R.layout.custom_snackbar_red

    init {
        View.inflate(
                context,
                layout,
                this
        )
    }

}

