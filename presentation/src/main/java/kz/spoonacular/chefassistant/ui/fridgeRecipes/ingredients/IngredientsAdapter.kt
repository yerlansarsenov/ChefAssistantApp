package kz.spoonacular.chefassistant.ui.fridgeRecipes.ingredients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.setImageWithUrlAndFit
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientItem

private const val TAG = "IngredientsAdapter"

class IngredientsAdapter(
    val listener: (text: String) -> Unit
): ListAdapter<IngredientItem, IngredientsAdapter.IngredientViewHolder>(IngredientDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.it_search_ingredient, parent, false
            )
        )

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class IngredientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: AppCompatTextView =
            itemView.findViewById(R.id.search_ingredient_textview)
        private val ingredientImageView: AppCompatImageView =
            itemView.findViewById(R.id.search_ingredient_imageview)

        fun bind(item: IngredientItem) {
            item.apply {
                ingredientTextView.text = name
                val baseImagesUrl = "https://spoonacular.com/cdn/ingredients_100x100/"
                ingredientImageView.setImageWithUrlAndFit(baseImagesUrl.plus(image))
                itemView.setOnClickListener {
                    listener.invoke(name)
                }
            }
        }
    }
}

class IngredientDiffUtil: DiffUtil.ItemCallback<IngredientItem>() {
    override fun areItemsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IngredientItem, newItem: IngredientItem): Boolean {
        return oldItem.name == newItem.name
    }

}