package com.example.recipeapp;

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.TempBinding

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

        inner class RecipeViewHolder(val binding: TempBinding) : RecyclerView.ViewHolder(binding.root)

        private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
                override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                        return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                        return oldItem == newItem
                }
        }

        private val differ = AsyncListDiffer(this, diffCallback)
        var recipes: List<Recipe>
                get() = differ.currentList
                set(value) { differ.submitList(value) }

        override fun getItemCount() = recipes.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
                return RecipeViewHolder(TempBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ))
        }
        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
                holder.binding.apply {
                        val recipe = recipes[position]
                        rvTitle.text = recipe.title
                        Log.e(ContentValues.TAG, recipe.title)
                }
        }

//        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
//                holder.binding.apply {
//                        val recipe = recipes[position]
//
//                }
//        }
}
