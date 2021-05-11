package com.fittingvroom.ui.fitting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentFittingBinding
import com.fittingvroom.ui.pick_up.ProductCardFragment
import com.fittingvroom.ui.view3d.SceneViewer


class FittingFragment : Fragment() {

    private var binding: FragmentFittingBinding? = null
    private val navigation by lazy { findNavController() }
    private var productId: Int? = null
    private var productSize: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFittingBinding.inflate(inflater, container, false)
        initToolbarNavigation()
        val view = binding?.root
        SceneViewer.showScene(context, resources, binding?.fittingSceneView, binding?.fittingPb)
        binding?.mannequinHelpImageBtn?.setOnClickListener {
            navigation.navigate(R.id.action_navigation_fitting_to_help_fragment)
        }
        binding?.fittingToPickUpButton?.setOnClickListener {
            navigation.navigate(R.id.action_navigation_fitting_to_pick_up_fragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productId = arguments?.getInt(ProductCardFragment.ID_PRODICT) ?: 0
        productSize = arguments?.getString(ProductCardFragment.SIZE_PRODICT) ?: ""
        if (productId == null || productSize.isNullOrEmpty()) {
            binding?.fittingFavoriteBtn?.visibility = View.GONE
            binding?.fittingCartAddBtn?.visibility = View.GONE
        }
    }

    private fun initToolbarNavigation() {
        binding?.apply {
            this.fittingToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
            this.fittingToolbar.setNavigationOnClickListener {
                navigation.popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.fittingSceneView?.resume()
    }


    override fun onPause() {
        super.onPause()
        binding?.fittingSceneView?.pause()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}