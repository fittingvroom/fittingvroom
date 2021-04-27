package com.fittingvroom.ui.model.parameters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentModelParametersBinding
import com.fittingvroom.utils.*

class ModelParametersFragment() : Fragment() {

    private val modelParameters by lazy { SharedPreferencesImplementation(requireContext()) }

    private lateinit var modelParametersViewModel: ModelParametersViewModel
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
        modelParametersViewModel = ViewModelProvider(this).get(ModelParametersViewModel::class.java)
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