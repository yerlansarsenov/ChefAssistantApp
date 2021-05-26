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
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.AnalyzedInstruction

class AnalyzedInstructionsAdapter(var context: Context? = null) :
    ListAdapter<AnalyzedInstruction, AnalyzedInstructionsAdapter.AnalyzedInstructionsHolder>(
        AnalyzedInstructionsItemCallback()
    ) {

    //val pool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyzedInstructionsHolder {
        return AnalyzedInstructionsHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.it_instruction,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: AnalyzedInstructionsHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: AnalyzedInstructionsHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        context = null
    }

    inner class AnalyzedInstructionsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: AppCompatTextView =
            itemView.findViewById(R.id.instruction_name)
        private val stepRecyclerView: RecyclerView =
            itemView.findViewById(R.id.instruction_recycler)

        fun bind(analyzedInstruction: AnalyzedInstruction) {
            nameTextView.text = analyzedInstruction.name
            val linearLayoutManager = LinearLayoutManager(context)
            val adapter = StepAdapter(context)
            stepRecyclerView.layoutManager = linearLayoutManager
            stepRecyclerView.adapter = adapter
            adapter.submitList(analyzedInstruction.steps)
        }

        fun unbind() {
            stepRecyclerView.apply {
                layoutManager = null
                adapter = null
            }
        }
    }

}

class AnalyzedInstructionsItemCallback: DiffUtil.ItemCallback<AnalyzedInstruction>() {
    override fun areItemsTheSame(
        oldItem: AnalyzedInstruction,
        newItem: AnalyzedInstruction
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: AnalyzedInstruction,
        newItem: AnalyzedInstruction
    ): Boolean {
        return oldItem.name == newItem.name
    }

}
