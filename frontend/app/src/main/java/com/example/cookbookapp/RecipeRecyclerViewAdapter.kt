package com.example.cookbookapp

import android.content.ContentValues
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.example.cookbookapp.databinding.FragmentRecipeBinding


class RecipeRecyclerViewAdapter(
    private var recipes: List<Recipe>,
) : RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = recipes[position]
        Log.e(ContentValues.TAG, recipes[position].title)
        holder.idView.text = item.title
    }

    override fun getItemCount(): Int = recipes.size

    inner class ViewHolder(binding: FragmentRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.title
    }

}