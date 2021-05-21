package kz.spoonacular.chefassistant.ui.searchRecipes

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.forEach
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.spoonacular.chefassistant.R
import java.io.Serializable

private const val TYPES_KEY = "types"

private const val CUISINES_KEY = "cuisines"

/**
 * Created by Sarsenov Yerlan on 05.02.2021.
 */
class FilterDialogFragment: AppCompatDialogFragment() {

    private var listener: (types: List<String>, cuisines: List<String>) -> Unit = { _, _ ->}

    fun setListener(listener: (types: List<String>, cuisines: List<String>) -> Unit) {
        this.listener = listener
    }

    companion object {
        fun instance(
            types: List<String>,
            cuisines: List<String>
        ): FilterDialogFragment {
            val fragment = FilterDialogFragment()
            val args = Bundle()
            args.let {
                val gson = Gson()
                val typesString = gson.toJson(types)
                val cuisinesString = gson.toJson(cuisines)
                it.putString(TYPES_KEY, typesString)
                it.putString(CUISINES_KEY, cuisinesString)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.difr_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cancelButton = view.findViewById<Button>(R.id.dialog_button_cancel)
        val applyButton = view.findViewById<Button>(R.id.dialog_button_apply)
        val typesGroup = view.findViewById<ChipGroup>(R.id.types_chip_group)
        val cuisinesGroup = view.findViewById<ChipGroup>(R.id.cuisines_chip_group)
        fun getChipTextById(id: Int) : String{
            val chip = view.findViewById<Chip>(id)
            return chip.text.toString()
        }
        val typeToken = object: TypeToken<List<String>>(){}.type
        val types: List<String> = (requireArguments()[TYPES_KEY] as String).let {
            Gson().fromJson(it, typeToken)
        }
        val cuisines: List<String> = (requireArguments()[CUISINES_KEY] as String).let {
            Gson().fromJson(it, typeToken)
        }
        types.forEach { text ->
            typesGroup.forEach { chip ->
                if ((chip as Chip).text == text) {
                    chip.isChecked = true
                }
            }
        }
        cuisines.forEach { text ->
            cuisinesGroup.forEach { chip ->
                if ((chip as Chip).text == text) {
                    chip.isChecked = true
                }
            }
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
        applyButton.setOnClickListener {
            val selectedTypes = typesGroup.checkedChipIds
            val selectedCuisines = cuisinesGroup.checkedChipIds
            listener.invoke(
                selectedTypes.map { id ->
                    getChipTextById(id)
                },
                selectedCuisines.map { id ->
                    getChipTextById(id)
                }
            )
            dismiss()
        }
    }

}
