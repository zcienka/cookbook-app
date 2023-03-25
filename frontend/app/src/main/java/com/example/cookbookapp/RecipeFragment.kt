package com.example.cookbookapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookapp.databinding.FragmentRecipeBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null

    private val binding get() = _binding!!

    private var columnCount = 1

    private var recipes = listOf<Recipe>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            columnCount = it.getInt(ARG_COLUMN_COUNT)
//        }
    }

//    interface OnItemClickListener {
//        fun onItemClick(position: Int)
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)

        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val api = retrofit.create(Api::class.java)

        fun updateRecyclerView(recipes: List<Recipe>) {
            Log.e(ContentValues.TAG, recipes.toString())

            if (view is RecyclerView) {
                with(view) {
                    layoutManager = when {
                        columnCount <= 1 -> LinearLayoutManager(context)
                        else -> GridLayoutManager(context, columnCount)
                    }
                    adapter = RecipeRecyclerViewAdapter(recipes )
                }
            }

        }

        api.getRecipes().enqueue(object : retrofit2.Callback<List<Recipe>> {
            override fun onResponse(
                call: retrofit2.Call<List<Recipe>>,
                response: retrofit2.Response<List<Recipe>>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val recipes = (response.body())!!
                    updateRecyclerView(recipes)
                } else {
                    Log.e(ContentValues.TAG, "Response not successful")
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Recipe>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Response not successful")
            }
        })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myLinearLayout.setOnClickListener {
            findNavController().navigate(R.id.action_recipeFragment_to_recipeDetailFragment)
        }


    }


}