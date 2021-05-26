package kz.spoonacular.chefassistant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.setImageWithUrl
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipes.Recipe


/**
 * Created by Sarsenov Yerlan on 05.02.2021.
 */
class RecipesByIngrAdapter(
    val listener: (id: Int) -> Unit
): ListAdapter<RecipeByIngredients, RecipesByIngrAdapter.RecipesViewHolder>(
    RecipesByIngrDiffUtil()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.it_recipeingr, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    inner class RecipesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_recipe_item_ingr)

        private val recipeImageView: ImageView = itemView.findViewById(R.id.image_recipe_item_ingr)

        private val countIngredients: TextView = itemView.findViewById(R.id.used_ingr_recipe_item_ingr)
        fun bind(recipe: RecipeByIngredients) {
            titleTextView.text = recipe.title
            recipeImageView.setImageWithUrl(recipe.image)
            countIngredients.text = "${countIngredients.text}${recipe.missedIngredientCount + recipe.usedIngredientCount}"
            itemView.setOnClickListener {
                listener.invoke(recipe.id)
            }
        }
    }

}

class RecipesByIngrDiffUtil: DiffUtil.ItemCallback<RecipeByIngredients>() {
    override fun areItemsTheSame(
        oldItem: RecipeByIngredients,
        newItem: RecipeByIngredients
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: RecipeByIngredients,
        newItem: RecipeByIngredients
    ): Boolean {
        return oldItem.title == newItem.title
    }

}