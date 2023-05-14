package com.example.cookbook

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.FragmentRecipeListBinding
import com.example.cookbook.models.Category
import com.example.cookbook.models.Recipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView? = binding.itemList

//        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)
//
//        if (recyclerView != null) {
//            setupRecyclerView(recyclerView, itemDetailFragmentContainer)
//        }
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        itemDetailFragmentContainer: View?,
    ) {
        RetrofitInstance.api.getRecipes().enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(
                call: Call<List<Recipe>>,
                response: Response<List<Recipe>>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val recipes = (response.body())!!

                    for (recipe in recipes) {
                        Log.e(ContentValues.TAG, recipe.title)
                    }

                    recyclerView.adapter = SimpleItemRecyclerViewAdapter(
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}