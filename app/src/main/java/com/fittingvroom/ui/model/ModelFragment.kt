package com.fittingvroom.ui.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.databinding.FragmentModelBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.ui.base.BaseFragment
import com.fittingvroom.ui.view3d.SceneViewer
import org.koin.android.viewmodel.ext.android.viewModel

class ModelFragment : BaseFragment<AppState<ModelParametersData>>() {

    override lateinit var model: ModelViewModel
    private var viewBinding: FragmentModelBinding? = null
    private val navigation by lazy { findNavController() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentModelBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        setBtnListeners()
        model.getData()
    }

    private fun initViewModel() {
        val modelViewModel : ModelViewModel by viewModel()
        model = modelViewModel
        model.subscribe().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.modelToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.modelToolbar.setNavigationOnClickListener {
            navigation.popBackStack()
        }
    }

    private fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.modelBottomButton.setOnClickListener {
            navigation.navigate(R.id.action_navigation_model_to_modelParametersFragment)
        }
        binding.mannequinHelpImageBtn.setOnClickListener {
            navigation.navigate(R.id.action_navigation_model_to_help_fragment)
        }
        binding.mannequinShareImageBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Поделиться", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        viewBinding?.modelSceneView?.pause()
        super.onPause()
    }
    override fun onResume() {
        viewBinding?.modelSceneView?.resume()
        super.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun renderData(state: AppState<ModelParametersData>) {
        when (state) {
            is AppState.Success -> {
                val dataModel : ModelParametersData = state.data
                showViewSuccess()
                if (dataModel.isSaved) {
                    showSceneView()
                    SceneViewer.showScene(context, resources, viewBinding?.modelSceneView, viewBinding?.modelSceneViewPb)
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
        binding.modelImageViewMannequin.visibility = View.GONE
        binding.modelSceneView.visibility = View.VISIBLE
        binding.modelSceneViewPb.visibility = View.GONE
    }

    private fun showImageView() {
        val binding = viewBinding ?: return
        binding.modelImageViewMannequin.visibility = View.VISIBLE
        binding.modelSceneView.visibility = View.GONE
        binding.modelSceneViewPb.visibility = View.GONE
    }

    private fun showViewSuccess() {
        val binding = viewBinding ?: return
        binding.modelSuccesLayout.visibility = View.VISIBLE
    }
}