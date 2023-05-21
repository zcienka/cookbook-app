import MyPagerAdapter
import android.content.ContentValues
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cookbook.CategoryRecyclerViewAdapter
import com.example.cookbook.R
import com.example.cookbook.RetrofitInstance
import com.example.cookbook.databinding.CategoryListBinding
import com.example.cookbook.models.Category
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListFragment : Fragment() {
    private var _binding: CategoryListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = CategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryRecyclerView: RecyclerView? = binding.categoryList
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)


        if (categoryRecyclerView != null) {
            setupCategoryRecyclerView(categoryRecyclerView, itemDetailFragmentContainer)
        }
    }

    private fun setupCategoryRecyclerView(
        recyclerView: RecyclerView,
        itemListFragmentContainer: View?,
    ) {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>,
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val categories = (response.body())!!

                    for (category in categories) {
                        Log.e(ContentValues.TAG, category.name)
                    }

                    var gridNum = 2

                    val isTablet =
                        (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE

                    if (isTablet) {
                        gridNum = 6
                    }

                    val layoutManager = GridLayoutManager(recyclerView.context, gridNum)

                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = CategoryRecyclerViewAdapter(
                        categories, itemListFragmentContainer
                    )
                } else {
                    Log.e(ContentValues.TAG, response.code().toString())
                    Log.e(ContentValues.TAG, "Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.e(ContentValues.TAG, "Response not successful")
                Log.e(ContentValues.TAG, t.message.toString())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
