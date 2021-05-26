package kz.spoonacular.chefassistant.ui.detailActivity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.setImageWithUrl
import kz.spoonacular.chefassistant.extensions.setImageWithUrlAndFit
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Equipment
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Ingredient
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Inquipment

class InquipmentsAdapter : ListAdapter<Inquipment, InquipmentsAdapter.InquipmentHolder>(
    InquipmentCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InquipmentHolder {
        return InquipmentHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    viewType,
                    parent,
                    false
                )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] is Ingredient) {
            R.layout.it_step_ingredient
        } else {
            R.layout.it_equipment
        }
    }

    override fun onBindViewHolder(holder: InquipmentHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class InquipmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: AppCompatTextView = itemView.findViewById(R.id.name_equipment_item)
        private val imageView: AppCompatImageView = itemView.findViewById(R.id.image_equipment_item)

        fun bind(item: Inquipment) {
            when (item) {
                is Ingredient -> {
                    nameTextView.text = item.name
                    imageView.setImageWithUrlAndFit(item.image, 80, 80)
                }
                is Equipment -> {
                    nameTextView.text = item.name
                    imageView.setImageWithUrlAndFit(item.image, 80, 80)
                }
            }
        }
    }
}

class InquipmentCallback : DiffUtil.ItemCallback<Inquipment>() {
    override fun areItemsTheSame(oldItem: Inquipment, newItem: Inquipment): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Inquipment, newItem: Inquipment): Boolean {
        if (oldItem is Ingredient && newItem is Ingredient) {
            return oldItem.id == newItem.id
        }
        if (oldItem is Equipment && newItem is Equipment) {
            return oldItem.id == newItem.id
        }
        return false
    }

}