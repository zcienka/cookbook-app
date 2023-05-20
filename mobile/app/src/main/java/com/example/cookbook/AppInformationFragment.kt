package com.example.cookbook

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.cookbook.databinding.AppInformationFragmentBinding

class AppInformationFragment : Fragment() {
    private var _binding: AppInformationFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = AppInformationFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val imageView = rootView.findViewById<ImageView>(R.id.item_image)
        val animator = ObjectAnimator.ofFloat(imageView, "translationY", 0f, -100f, 0f)
        animator.duration = 1500
        animator.repeatCount = ObjectAnimator.INFINITE
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()

        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}