package com.fittingvroom.ui.fitting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.databinding.FragmentFittingBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.ui.base.BaseFragment
import com.fittingvroom.ui.pick_up.ProductCardFragment
import com.fittingvroom.ui.view3d.SceneViewer
import org.koin.android.viewmodel.ext.android.viewModel

class FittingFragment : BaseFragment<AppState<ModelParametersData>>() {

    override lateinit var model: FittingViewModel
    private var viewBinding: FragmentFittingBinding? = null
    private val navigation by lazy { findNavController() }
    private var productId: Int? = null
    private var productSize: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentFittingBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        setBtnListeners()
        productId = arguments?.getInt(ProductCardFragment.ID_PRODICT) ?: 0
        productSize = arguments?.getString(ProductCardFragment.SIZE_PRODICT) ?: ""
        if (productId == null || productSize.isNullOrEmpty()) {
            viewBinding?.fittingFavoriteImageBtn?.visibility = View.GONE
            viewBinding?.fittingCartAddBtn?.visibility = View.GONE
        }
        model.getData()
    }

    private fun initViewModel() {
        val viewModel: FittingViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.fittingToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.fittingToolbar.setNavigationOnClickListener {
            navigation.popBackStack()
        }
    }

    private fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.fittingBottomButton.setOnClickListener {
            navigation.navigate(R.id.action_navigation_fitting_to_pick_up_fragment)
        }
        binding.fittingHelpImageBtn.setOnClickListener {
            navigation.navigate(R.id.action_navigation_fitting_to_help_fragment)
        }
        binding.fittingShareImageBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Поделиться", Toast.LENGTH_SHORT).show()
        }
        binding.fittingFavoriteImageBtn.setOnClickListener {

        }
    }

    override fun onPause() {
        viewBinding?.fittingSceneView?.pause()
        super.onPause()
    }

    override fun onResume() {
        viewBinding?.fittingSceneView?.resume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun renderData(state: AppState<ModelParametersData>) {
        when (state) {
            is AppState.Success -> {
                val dataModel: ModelParametersData = state.data
                showViewSuccess()
                if (dataModel.isSaved) {
                    showSceneView()
                    SceneViewer.showScene(
                        requireContext(),
                        resources,
                        viewBinding?.fittingSceneView,
                        viewBinding?.fittingSceneViewPb
                    )
                } else {
                    showImageView()
                }
            }
            is AppState.Error -> {

            }
        }
    }

    private fun showSceneView() {
        val binding = viewBinding ?: return
        binding.fittingImageViewMannequin.visibility = View.GONE
        binding.fittingSceneView.visibility = View.VISIBLE
        binding.fittingSceneViewPb.visibility = View.GONE
    }

    private fun showImageView() {
        val binding = viewBinding ?: return
        binding.fittingImageViewMannequin.visibility = View.VISIBLE
        binding.fittingSceneView.visibility = View.GONE
        binding.fittingSceneViewPb.visibility = View.GONE
    }

    private fun showViewSuccess() {
        val binding = viewBinding ?: return
        binding.fittingSuccesLayout.visibility = View.VISIBLE
    }
}