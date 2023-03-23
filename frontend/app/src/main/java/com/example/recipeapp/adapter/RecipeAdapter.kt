package com.example.recipeapp.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Recipe
import com.example.recipeapp.databinding.FragmentRecipeBinding

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: FragmentRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)

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
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = recipes.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            FragmentRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.apply {
            val recipe = recipes[position]
            rvTitle.text = recipe.title
            Log.e(ContentValues.TAG, recipe.title)
        }
    }

//    interface OnItemClickListener {
//        fun onClicked(categoryName: String)
//    }

//        override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//                setContentView(R.layout.activity_new)
//                title = "KotlinApp" val button: Button = findViewById(R.id.btnOpenMain)
//                button.setOnClickListener {
//                        val i = Intent(this@NewActivity, MainActivity::class.java)
//                        startActivity(i)
//                }
//        }
}


