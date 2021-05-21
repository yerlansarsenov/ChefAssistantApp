package kz.spoonacular.chefassistant.ui.detailActivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient

class ExtendedIngredientsAdapter:
    RecyclerView.Adapter<ExtendedIngredientsAdapter.ExtendedIngredientsViewHolder>() {

    private val list: MutableList<ExtendedIngredient> = mutableListOf()

    fun submitList(list: List<ExtendedIngredient>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtendedIngredientsViewHolder =
        ExtendedIngredientsViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.it_ingredient,
                    parent,
                    false
                )
        )

    override fun onBindViewHolder(holder: ExtendedIngredientsViewHolder, position: Int) {
        if (position == 0) {
            holder.bindHeader()
        } else {
            holder.bind(list[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    inner class ExtendedIngredientsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ingredientNameTextView: AppCompatTextView
            = itemView.findViewById(R.id.ingredient_name)
        private val ingredientAmountTextView: AppCompatTextView
            = itemView.findViewById(R.id.ingredient_amount)
        private val ingredientUnitTextView: AppCompatTextView
            = itemView.findViewById(R.id.ingredient_unit)
        private val ingredientConsistencyTextView: AppCompatTextView
            = itemView.findViewById(R.id.ingredient_consistency)

        fun bind(ingredient: ExtendedIngredient) {
            ingredient.let { item ->
                ingredientNameTextView.text = item.name
                ingredientAmountTextView.text = item.amount.toString()
                ingredientUnitTextView.text = item.unit
                ingredientConsistencyTextView.text = item.consistency
            }
        }

        fun bindHeader() {
            val s = "name"
            val s1 = "amount"
            val s2 = "unit"
            val s3 = "consistency"
            itemView.setBackgroundColor(Color.GRAY)
            ingredientNameTextView.apply {
                text = s
            }
            ingredientAmountTextView.apply {
                text = s1
            }
            ingredientUnitTextView.apply {
                text = s2
            }
            ingredientConsistencyTextView.apply {
                text = s3
            }
        }
    }
}

class ExtendedIngredientsCallBack: DiffUtil.ItemCallback<ExtendedIngredient>() {
    override fun areItemsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem.original == newItem.original
    }

}
