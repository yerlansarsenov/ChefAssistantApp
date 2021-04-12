package kz.spoonacular.chefassistant.customViews.custom_snackbar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import kz.spoonacular.chefassistant.R

class CustomSnackbarViewRed @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {

    val layout: Int = R.layout.custom_snackbar_red

    init {
        View.inflate(
                context,
                layout,
                this
        )
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val cardView = this.findViewById<CardView>(R.id.custom_toast_container)
        cardView.animate()
            .alpha(1f)
            .setDuration(1000)
            .start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
        val cardView = this.findViewById<CardView>(R.id.custom_toast_container)
        val scaleX = ObjectAnimator.ofFloat(cardView, View.SCALE_X, 1f, 0f)
        val scaleY = ObjectAnimator.ofFloat(cardView, View.SCALE_Y, 1f, 0f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(2000)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

}

