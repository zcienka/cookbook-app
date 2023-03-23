package com.example.recipeapp

import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit

class RecipeDetailsActivity : MainActivity() {

//    private val onCLickedSubItem = object : RecipeAdapter.OnItemClickListener {
//        override fun onClicked(id: String) {
//            var intent = Intent(this@MainActivity, RecipeDetailsActivity::class.java)
//            intent.putExtra("id", id)
//            startActivity(intent)
//        }
//    }
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    if (savedInstanceState == null) {
//        supportFragmentManager.commit {
//            setReorderingAllowed(true)
//            add<ExampleFragment>(R.id.fragment_container_view)
//        }
//    }
//}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

//        var id = intent.getStringExtra("id")
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RecipeFragment>(R.id.fragment_container_view)
            }
        }
        val fragment: RecipeFragment =
            supportFragmentManager.findFragmentById(R.id.rvTitle) as RecipeFragment
    }
}
