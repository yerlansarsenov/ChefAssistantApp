package kz.spoonacular.chefassistant.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.setImageWithUrl
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
class RecipesAdapter(
    val listener: (id: Int) -> Unit
): ListAdapter<Recipe, RecipesAdapter.RecipesViewHolder>(RecipeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.it_recipe, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class RecipesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val titleTextView: TextView = itemView.findViewById(R.id.title_recipe_item)
        private val recipeImageView: ImageView = itemView.findViewById(R.id.image_recipe_item)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            recipeImageView.setImageWithUrl(recipe.image)
            itemView.setOnClickListener {
                listener.invoke(recipe.id)
            }
        }
    }
}

class RecipeDiffUtil: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.title == newItem.title
    }

}