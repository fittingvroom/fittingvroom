package com.fittingvroom.ui.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentModelBinding

class ModelFragment : Fragment() {

    private lateinit var modelViewModel: ModelViewModel
    private var viewBinding: FragmentModelBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        viewBinding = FragmentModelBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        setBtnListeners()
    }

    private fun initViewModel() {
        modelViewModel =
                ViewModelProvider(this).get(ModelViewModel::class.java)
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.modelToolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.modelToolbar.setNavigationOnClickListener {
            Toast.makeText(requireContext(), "Возврат к предыдущему фрагменту", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.modelBottomButton.setOnClickListener {
            Toast.makeText(requireContext(), "К окну добавления параметров", Toast.LENGTH_SHORT).show()
        }
        binding.mannequinHelpImageBtn.setOnClickListener {
            Toast.makeText(requireContext(), "К окну помощи", Toast.LENGTH_SHORT).show()
        }
        binding.mannequinShareImageBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Поделиться", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}