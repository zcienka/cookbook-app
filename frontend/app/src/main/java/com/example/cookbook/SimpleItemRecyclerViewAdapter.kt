package com.example.cookbook

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.databinding.RecipeListContentBinding
import com.squareup.picasso.Picasso

class SimpleItemRecyclerViewAdapter(
    private val values: List<Recipe>,
    private val itemDetailFragmentContainer: View?,
) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecipeListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.titleView.setTypeface(null, Typeface.BOLD)
        holder.titleView.setTextColor(Color.BLACK)

        holder.caloriesView.text = item.calories
        holder.timeView.text = item.preparation_time

        Picasso.get()
            .load("http://10.0.2.2:8000/media/image0.jpg")
            .placeholder(R.drawable.image_missing)
            .into(holder.imageView)

        with(holder.itemView) {
            tag = item
            setOnClickListener { itemView ->
                val item = itemView.tag as Recipe
                val bundle = Bundle()
                bundle.putString(
                    ItemDetailFragment.ARG_ITEM_ID,
                    item.id,
                )
                bundle.putSerializable("recipes", values as java.io.Serializable)

                if (itemDetailFragmentContainer != null) {
                    itemDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
                } else {
                    itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                }
            }
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: RecipeListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.content
        var caloriesView: TextView = binding.calories
        val timeView: TextView = binding.time

        val imageView: ImageView = binding.itemImage
    }
}