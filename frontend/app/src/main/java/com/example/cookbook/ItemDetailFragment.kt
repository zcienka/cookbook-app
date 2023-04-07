package com.example.cookbook

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cookbook.databinding.FragmentRecipeDetailBinding
import com.squareup.picasso.Picasso


class ItemDetailFragment : Fragment() {

    private var item: Recipe? = null

    lateinit var itemDetailTextView: TextView
    lateinit var itemImageView: ImageView
    lateinit var itemCaloriesTextView: TextView
    lateinit var itemPreparationTimeTextView: TextView
    lateinit var itemDescription: TextView

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
        itemCaloriesTextView = binding.itemCalories!!
        itemPreparationTimeTextView = binding.itemPreparationTime!!
        itemDescription = binding.itemDescription!!
//        itemRecipe = binding.itemRecipe!!

        updateContent()

        return rootView
    }

    private fun updateContent() {
        item?.let {
            itemDetailTextView.text = it.title
            itemDetailTextView.setTypeface(null, Typeface.BOLD);
            itemCaloriesTextView.text = it.calories
            itemPreparationTimeTextView.text = it.preparation_time
            itemDescription.text = it.description

            Picasso.get()
                .load("http://10.0.2.2:8000/media/image0.jpg")
                .placeholder(R.drawable.alert_dark_frame)
                .into(itemImageView)
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