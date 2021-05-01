package com.fittingvroom.ui.model.parameters

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentModelParametersBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        initGenderTextView()
        addDecimalLimiter()
        modelParametersViewModel.getData()
    }

    private fun addDecimalLimiter() {
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
            getString(R.string.genfer_list_female),
            getString(R.string.genfer_list_male))
        val adapter = ArrayAdapter(requireContext(), R.layout.gender_list_item, items)
        viewBinding?.genderTextView?.setAdapter(adapter)
    }

    private fun initViewModel() {
        val viewModel: ModelParametersViewModel by viewModel()
        modelParametersViewModel = viewModel
        modelParametersViewModel.subscribe()
            .observe(viewLifecycleOwner , { renderData(it) })
    }

    private fun renderData(parametersData: ModelParametersData) {
        val binding = viewBinding ?: return
        binding.paramHeigthView.text = parametersData.height as Editable
        binding.paramChestGirthView.text = parametersData.chestGirth as Editable
        binding.paramHipsGirthView.text = parametersData.hipsGirth as Editable
        binding.paramWaistGirthView.text = parametersData.waistGirth as Editable
        binding.paramChestWidthView.text = parametersData.chestWidth as Editable
        binding.paramBackWidthView.text = parametersData.backWidth as Editable
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.modelParametersToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.modelParametersToolbar.setNavigationOnClickListener {
            navigation.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}