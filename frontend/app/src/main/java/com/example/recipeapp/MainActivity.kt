package com.example.recipeapp

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.adapter.RecipeAdapter
import com.example.recipeapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import retrofit2.HttpException
import java.io.IOException

open class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {




        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)

        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
//            binding.progressBar.isVisible = true

            val response = try {
                RetrofitInstance.api.getRecipes()
            } catch(e: IOException) {
//                Log.e(TAG, e.message)
                e.printStackTrace()
//                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
//                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                recipeAdapter.recipes = response.body()!!
                Log.e(TAG, "Response successful")

            } else {
                Log.e(TAG, "Response not successful")
            }
//            binding.progressBar.isVisible = false
        }


//        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
//        sideNavView?.setupWithNavController(navController)

    }

//    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
//        override fun onClicked(categoryName: String) {
//            getMealDataFromDb(categoryName)
//        }
//    }


    private fun setupRecyclerView() = binding.rvRecipes.apply {
        recipeAdapter = RecipeAdapter()
        adapter = recipeAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}