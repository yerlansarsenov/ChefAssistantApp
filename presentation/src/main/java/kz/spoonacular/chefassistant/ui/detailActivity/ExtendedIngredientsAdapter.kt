package kz.spoonacular.chefassistant.ui.detailActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient

class IngredientsAdapter: ListAdapter<ExtendedIngredient, IngredientsAdapter.IngredientsViewHolder>(IngredientsCallBack()) {


    inner class IngredientsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder(
            itemView = LayoutInflater.from(parent).
        )

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

class IngredientsCallBack: DiffUtil.ItemCallback<ExtendedIngredient>() {
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