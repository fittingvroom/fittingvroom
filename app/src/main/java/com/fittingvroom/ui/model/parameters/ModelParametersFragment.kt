package com.fittingvroom.ui.model.parameters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.databinding.FragmentModelParametersBinding
import com.fittingvroom.ui.base.BaseFragment
import com.fittingvroom.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class ModelParametersFragment : BaseFragment<ModelParametersData>() {

    override lateinit var model: ModelParametersViewModel
    private var viewBinding: FragmentModelParametersBinding? = null
    private val navigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentModelParametersBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        setBtnListeners()
        initGenderTextView()
        decimalLimiter()
        model.getData()
    }

    private fun decimalLimiter() {
        val binding = viewBinding ?: return
        binding.paramHeigthView.addDecimalLimiter()
        binding.paramChestGirthView.addDecimalLimiter()
        binding.paramHipsGirthView.addDecimalLimiter()
        binding.paramWaistGirthView.addDecimalLimiter()
        binding.paramChestWidthView.addDecimalLimiter()
        binding.paramBackWidthView.addDecimalLimiter()
    }

    private fun initGenderTextView() {
        val items = listOf(
            getString(R.string.gender_list_female),
            getString(R.string.gender_list_male)
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.gender_list_item, items)
        viewBinding?.genderTextView?.setAdapter(adapter)
    }

    private fun initViewModel() {
        val viewModel: ModelParametersViewModel by viewModel()
        model = viewModel
        model.subscribe()
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun renderData(state: ModelParametersData) {
        val binding = viewBinding ?: return
        binding.genderTextView.setText(state.gender)
        binding.paramHeigthView.setText(state.height)
        binding.paramChestGirthView.setText(state.chestGirth)
        binding.paramHipsGirthView.setText(state.hipsGirth)
        binding.paramWaistGirthView.setText(state.waistGirth)
        binding.paramChestWidthView.setText(state.chestWidth)
        binding.paramBackWidthView.setText(state.backWidth)
        initGenderTextView()
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.modelParametersToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.modelParametersToolbar.setNavigationOnClickListener {
            navigation.popBackStack()
        }
    }

    fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.modelParametersSaveButton.setOnClickListener {
            if (saveParameters()) {
                navigation.popBackStack(R.id.navigation_home, false)
                navigation.navigate(R.id.action_navigation_home_to_navigation_model)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_parameters_error), Toast.LENGTH_SHORT).show()
            }
        }
        binding.modelParametersContinueButton.setOnClickListener {
            if (saveParameters()) {
                navigation.popBackStack(R.id.navigation_model, false)
                navigation.navigate(R.id.navigation_fitting)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_parameters_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveParameters() : Boolean {
        val binding = viewBinding ?: return false
        return model.saveParameters(
            ModelParametersData(
                false,
                binding.genderTextView.text.toString(),
                binding.paramHeigthView.text.toString(),
                binding.paramChestGirthView.text.toString(),
                binding.paramHipsGirthView.text.toString(),
                binding.paramWaistGirthView.text.toString(),
                binding.paramChestWidthView.text.toString(),
                binding.paramBackWidthView.text.toString()
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}

