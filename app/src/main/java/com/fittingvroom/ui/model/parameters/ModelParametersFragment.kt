package com.fittingvroom.ui.model.parameters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.data.ModelParametersData
import com.fittingvroom.databinding.FragmentModelParametersBinding
import com.fittingvroom.utils.*
import org.koin.android.viewmodel.ext.android.viewModel

class ModelParametersFragment : Fragment() {

    lateinit var modelParametersViewModel: ModelParametersViewModel
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
        modelParametersViewModel.getData()
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
        modelParametersViewModel = viewModel
        modelParametersViewModel.subscribe()
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun renderData(parametersData: ModelParametersData) {
        val binding = viewBinding ?: return
        binding.genderTextView.setText(parametersData.gender)
        binding.paramHeigthView.setText(parametersData.height)
        binding.paramChestGirthView.setText(parametersData.chestGirth)
        binding.paramHipsGirthView.setText(parametersData.hipsGirth)
        binding.paramWaistGirthView.setText(parametersData.waistGirth)
        binding.paramChestWidthView.setText(parametersData.chestWidth)
        binding.paramBackWidthView.setText(parametersData.backWidth)
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
                navigation.navigate(R.id.navigation_model)
            } else {
                Toast.makeText(requireContext(), "Введите все параметры!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.modelParametersContinueButton.setOnClickListener {
            if (saveParameters()) {
                navigation.popBackStack(R.id.navigation_model, false)
                navigation.navigate(R.id.navigation_fitting)
            } else {
                Toast.makeText(requireContext(), "Введите все параметры!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveParameters() : Boolean {
        val binding = viewBinding ?: return false
        return modelParametersViewModel.saveParameters(
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

