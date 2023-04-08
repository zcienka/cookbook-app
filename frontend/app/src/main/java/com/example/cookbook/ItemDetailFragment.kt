package com.example.cookbook

import android.R
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cookbook.databinding.FragmentRecipeDetailBinding
import com.squareup.picasso.Picasso


class ItemDetailFragment : Fragment() {

    private var item: Recipe? = null

    lateinit var itemDetailTextView: TextView
    lateinit var itemImageView: ImageView
    lateinit var itemCaloriesTextView: TextView
    private lateinit var itemPreparationTimeTextView: TextView
    lateinit var itemDescription: TextView
    lateinit var itemIngredients: TextView
    lateinit var itemRecipe: TextView
    lateinit var button: AppCompatButton
    lateinit var itemContainer: View

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.getSerializable("recipes") != null) {
            val recipes = arguments?.getSerializable("recipes") as List<Recipe>
            arguments?.let {
                if (it.containsKey(ARG_ITEM_ID)) {
                    item = recipes.find { recipe: Recipe -> recipe.id == it.getString(ARG_ITEM_ID) }
                }
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
        itemImageView = binding.itemImage
        itemCaloriesTextView = binding.itemCalories
        itemPreparationTimeTextView = binding.itemPreparationTime
        itemDescription = binding.itemDescription
        itemIngredients = binding.itemIngredients
        itemRecipe = binding.itemRecipe
        button = binding.goBackBtn
        itemContainer = binding.itemDetailContainer

        val isTablet =
            (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

        if (isTablet) {
            button.visibility = View.INVISIBLE
        } else {
            button.setOnClickListener {
                it.findNavController().navigateUp()
            }
        }

        updateContent()

        return rootView
    }

    @SuppressLint("SetTextI18n")
    private fun updateContent() {
        if (item == null) {
            itemContainer.visibility = View.GONE
        }

        item?.let {
            itemDetailTextView.text = it.title
            itemDetailTextView.setTypeface(null, Typeface.BOLD)
            itemCaloriesTextView.text = it.calories + " kcal"
            itemPreparationTimeTextView.text = getTime(it.preparation_time.toInt())
            itemDescription.text = it.description
            itemIngredients.text = getFormattedIngredients(it.ingredients)
            itemRecipe.text = it.recipe

            Picasso.get()
                .load("http://10.0.2.2:8000/${it.image}")
                .placeholder(R.drawable.alert_dark_frame)
                .into(itemImageView)
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

    private fun getFormattedIngredients(ingredients: String): String {
        val ingredientsList = ingredients.split(", ").toTypedArray()
        var formattedIngredients = ""

        for (ingredient in ingredientsList) {
            formattedIngredients += "\u25CF $ingredient\n"
        }
        return formattedIngredients
    }


    companion object {
        const val ARG_ITEM_ID = "id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}