package kz.spoonacular.chefassistant.ui.detailActivity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Step

class StepAdapter(var context: Context? = null) : ListAdapter<Step, StepAdapter.StepHolder>(
    StepItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        return StepHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.it_step,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: StepHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    inner class StepHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: AppCompatTextView = itemView.findViewById(R.id.step_name)
        private val equipmentRecyclerView: RecyclerView = itemView.findViewById(R.id.step_recycler_eq)
        private val ingredientRecyclerView: RecyclerView = itemView.findViewById(R.id.step_recycler_ing)

        fun bind(step: Step) {
            nameTextView.text = step.step
            val linearLayoutManagerEquipment = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val linearLayoutManagerIngredient = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val equipmentAdapter = InquipmentsAdapter()
            val ingredientsAdapter = InquipmentsAdapter()

            equipmentRecyclerView.layoutManager = linearLayoutManagerEquipment
            equipmentRecyclerView.adapter = equipmentAdapter
            equipmentAdapter.submitList(step.equipments)

            ingredientRecyclerView.layoutManager = linearLayoutManagerIngredient
            ingredientRecyclerView.adapter = ingredientsAdapter
            ingredientsAdapter.submitList(step.ingredients)
        }

        fun unbind() {
            equipmentRecyclerView.apply {
                layoutManager = null
                adapter = null
            }
            ingredientRecyclerView.apply {
                layoutManager = null
                adapter = null
            }
        }
    }
}

class StepItemCallback: DiffUtil.ItemCallback<Step>() {
    override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem.step == newItem.step
    }

}
