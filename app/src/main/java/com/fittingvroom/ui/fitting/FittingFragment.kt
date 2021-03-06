package com.fittingvroom.ui.fitting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.databinding.FragmentFittingBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.ui.base.BaseFragment
import com.fittingvroom.ui.pick_up.PickUpRvViewModel
import com.fittingvroom.ui.pick_up.ProductCardFragment
import com.fittingvroom.ui.view3d.SceneViewer
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class FittingFragment : BaseFragment<AppState<ModelParametersData>>() {

    override lateinit var model: FittingViewModel
    lateinit var productCardModel: PickUpRvViewModel
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
        hideButtonsIfNeeded()
        model.getData()
    }

    private fun hideButtonsIfNeeded() {
        productId = arguments?.getInt(ProductCardFragment.ID_PRODICT) ?: 0
        productSize = arguments?.getString(ProductCardFragment.SIZE_PRODICT) ?: ""
        if (productId == null || productSize.isNullOrEmpty()) {
            viewBinding?.fittingFavoriteImageBtn?.visibility = View.GONE
            viewBinding?.fittingFavoriteImageOnBtn?.visibility = View.GONE
            viewBinding?.fittingBasketBtn?.visibility = View.GONE
        } else {
            productCardModel.getFavorites(productId!!).observe(viewLifecycleOwner, { isFavorite ->
                showFavorite(isFavorite)
            })
        }
    }

    private fun showFavorite(isFavorite: Boolean) {
        val binding = viewBinding ?: return
        if (isFavorite) {
            binding.fittingFavoriteImageBtn.visibility = View.GONE
            binding.fittingFavoriteImageOnBtn.visibility = View.VISIBLE
        } else {
            binding.fittingFavoriteImageBtn.visibility = View.VISIBLE
            binding.fittingFavoriteImageOnBtn.visibility = View.GONE
        }
    }

    private fun initViewModel() {
        val pickUpRvViewModel: PickUpRvViewModel by viewModel()
        productCardModel = pickUpRvViewModel

        val viewModel: FittingViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, Observer {renderData(it) })
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
            Toast.makeText(requireContext(), "????????????????????", Toast.LENGTH_SHORT).show()
        }
        binding.fittingFavoriteImageBtn.setOnClickListener {

        }
        binding.fittingBasketBtn.setOnClickListener {
            productCardModel.setBasket(productId!!, productSize!!)
            Snackbar.make(
                    binding.fittingBasketBtn,
                    R.string.added_to_basket,
                    Snackbar.LENGTH_SHORT
            )
                    .show()
        }
        binding.fittingFavoriteImageBtn.setOnClickListener {
            productCardModel.setFavorites(productId!!)
            showFavorite(true)

        }
        binding.fittingFavoriteImageOnBtn.setOnClickListener {
            productCardModel.setFavorites(productId!!)
            showFavorite(false)
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
                        viewBinding?.fittingSceneViewPb,
                        !(productId == null || productSize.isNullOrEmpty())
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