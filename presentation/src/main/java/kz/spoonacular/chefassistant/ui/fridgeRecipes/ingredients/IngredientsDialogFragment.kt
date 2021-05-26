package kz.spoonacular.chefassistant.ui.fridgeRecipes.ingredients

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

/**
 * Created by Sarsenov Yerlan on 25.02.2021.
 */
class IngredientsDialogFragment : DialogFragment() {

    private var listener: (text: String) -> Unit = {}

    private val viewModel: IngredientsViewModel by viewModel()

    private var recyclerView: RecyclerView? = null
    private var searchView: AppCompatEditText? = null
    private var closeIcon: AppCompatImageView? = null

    private val adapter: IngredientsAdapter by lazy { IngredientsAdapter(listener) }

    fun setListener(listener: (text: String) -> Unit) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.difr_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.ingredients_recycler_view)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = adapter
        recyclerView!!.addItemDecoration(
            DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        )
        searchView = view.findViewById(R.id.ingredients_search_view)
        searchView!!.addTextChangedListener { query ->
            if (query != null && query.isNotEmpty()) {
                viewModel.searchIngredients(query.toString())
            }
        }
        /*searchView!!.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    //viewModel.searchIngredients(query)
                    listener.invoke(query)
                    searchView!!.clearFocus()
                    return true
                }
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    viewModel.searchIngredients(query)
                    return true
                }
                return false
            }
        })*/
        /*searchView!!.setOnCloseListener {
            viewModel.apply {
                searchIngredients("")
            }
            false
        }*/
        closeIcon = view.findViewById(R.id.close_icon)
        closeIcon!!.setOnClickListener {
            viewModel.apply {
                searchIngredients("")
            }
            dismiss()
        }
        searchView!!.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                closeIcon!!.visibility = View.VISIBLE
            } else {
                closeIcon!!.visibility = View.GONE
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Either.Success -> {
                    response.response.results.let { list ->
                       /* if (list.isNotEmpty() && list[0].name == searchView?.query.toString())  {
                            listener.invoke(list[0].name)
                            dismiss()
                        } else {*/
                        adapter.submitList(list)
                        /*}*/
                    }
                }
                is Either.Error -> {
                    showToast(response.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        recyclerView?.apply {
            adapter = null
            layoutManager = null
        }
        recyclerView = null
        searchView = null
        closeIcon = null
        super.onDestroyView()
    }

}