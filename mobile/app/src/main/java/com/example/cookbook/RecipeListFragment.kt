package com.example.cookbook

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.RecipeListBinding
import com.example.cookbook.models.Category
import com.example.cookbook.models.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast

class RecipeListFragment : Fragment() {
    private var _binding: RecipeListBinding? = null
    private val binding get() = _binding!!
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.getSerializable("category") != null) {
            category = arguments?.getSerializable("category") as String
        }


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = RecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        val backButton: ImageView = view.findViewById(R.id.back_button)
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val fab: FloatingActionButton = view.findViewById(R.id.fab)

        fab.setOnClickListener {
            val message = "Updating recipes..."
            val context = requireContext()
            context.toast(message)
            fetchRecipesByCategory(category, recyclerView, itemDetailFragmentContainer)
        }

        setupRecyclerView(recyclerView, itemDetailFragmentContainer)
    }

    private fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?,
    ) {
        category?.let {
            fetchRecipesByCategory(category, recyclerView, itemDetailFragmentContainer)
        }
    }


    private fun fetchRecipesByCategory(category: String?, recyclerView: RecyclerView, itemDetailFragmentContainer: View?) {
        category?.let {
            RetrofitInstance.api.getRecipesByCategory(it).enqueue(object : Callback<List<Recipe>> {
                override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                    if (response.isSuccessful && response.body() != null) {
                        val recipes = response.body()!!

                        recyclerView.adapter = RecipeItemRecyclerViewAdapter(
                            recipes, itemDetailFragmentContainer
                        )
                    } else {
                        Log.e(ContentValues.TAG, response.code().toString())
                        Log.e(ContentValues.TAG, "Response not successful")
                    }
                }

                override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                    Log.e(ContentValues.TAG, "Response not successful")
                    Log.e(ContentValues.TAG, t.message.toString())
                }
            })
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}