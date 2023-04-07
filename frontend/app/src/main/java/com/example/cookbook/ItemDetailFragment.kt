package com.example.cookbook

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.cookbook.databinding.FragmentRecipeDetailBinding
import com.squareup.picasso.Picasso

class ItemDetailFragment : Fragment() {

    private var item: Recipe? = null

    lateinit var itemDetailTextView: TextView
    lateinit var itemImageView: ImageView

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recipes = arguments?.getSerializable("recipes") as List<Recipe>

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = recipes.find { recipe: Recipe -> recipe.id == it.getString(ARG_ITEM_ID) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        itemDetailTextView = binding.itemDetail
        itemImageView = binding.itemImage!!

        updateContent()

        return rootView
    }

    private fun updateContent() {
        item?.let {
            itemDetailTextView.text = it.title
            itemDetailTextView.setTypeface(null, Typeface.BOLD);

            Picasso.get()
                .load("http://10.0.2.2:8000/media/image0.jpg")
                .placeholder(R.drawable.image_missing)
                .into(itemImageView);
        }
    }
    companion object {
        const val ARG_ITEM_ID = "id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}