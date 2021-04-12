package kz.spoonacular.chefassistant.ui.adapter

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
import kz.spoonacular.domain.model.recipes.Recipe

private const val TAG = "IngredientsAdapter"

class RecipesSearchableAdapter(
    val listener: (text: String) -> Unit
): ListAdapter<Recipe, RecipesSearchableAdapter.IngredientViewHolder>(IngredientDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.it_search_recipe, parent, false
            )
        )

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class IngredientViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: AppCompatTextView = itemView.findViewById(R.id.search_recipe_textview)

        fun bind(item: Recipe) {
            item.apply {
                ingredientTextView.text = title
                itemView.setOnClickListener {
                    listener.invoke(title)
                }
            }
        }
    }
}

class IngredientDiffUtil: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.title == newItem.title
    }

}