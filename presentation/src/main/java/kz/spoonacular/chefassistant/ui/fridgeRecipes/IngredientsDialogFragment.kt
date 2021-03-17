package kz.spoonacular.chefassistant.ui.fridgeRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kz.spoonacular.chefassistant.R

/**
 * Created by Sarsenov Yerlan on 25.02.2021.
 */
class IngredientsDialogFragment : DialogFragment() {

    private var listener: () -> Unit = {}

    fun setListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.difr_filter, container, false)
    }

}