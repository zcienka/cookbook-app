package com.example.cookbook

import android.annotation.SuppressLint
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.titleView.setTypeface(null, Typeface.BOLD)
        holder.titleView.setTextColor(Color.BLACK)

        holder.caloriesView.text = item.calories + " kcal"
        holder.timeView.text = getTime(item.preparation_time.toInt())

        Picasso.get()
            .load("http://10.0.2.2:8000/${item.image}")
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

    private fun getTime(minutes: Int): String {
        val hours = minutes / 60
        val minutesRemaining = minutes % 60
        val hoursString = if (hours == 1) "godzina" else "godziny"
        val minutesString = if (minutesRemaining == 1) "minuta" else "minut"
        return when {
            hours > 0 && minutesRemaining > 0 -> "$hours $hoursString i $minutesRemaining $minutesString"
            hours > 0 -> "$hours $hoursString"
            minutesRemaining > 0 -> "$minutesRemaining $minutesString"
            else -> "0 $minutesString"
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