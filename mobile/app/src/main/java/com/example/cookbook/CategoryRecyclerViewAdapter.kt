package com.example.cookbook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.cookbook.databinding.CategoryItemBinding
import com.example.cookbook.models.Category
import com.squareup.picasso.Picasso

class CategoryRecyclerViewAdapter(
    private val values: List<Category>,
    private val itemListContainer: View?,
    ) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]


        holder.idView.text = item.name

        Picasso.get()
            .load("http://10.0.2.2:8000${item.url}")
            .placeholder(R.drawable.image_missing)
            .into(holder.imageView)

        with(holder.itemView) {
            tag = item
            setOnClickListener { itemView ->
                val item = itemView.tag as Category
                val bundle = Bundle()
                bundle.putString(
                    ItemDetailFragment.ARG_ITEM_ID,
                    item.name,
                )

                bundle.putString("category", item.name)

                if (itemListContainer != null) {
                    itemListContainer.findNavController()
                        .navigate(R.id.recipe_list, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.recipe_list, bundle)
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val imageView: ImageView = binding.itemImage
    }
}