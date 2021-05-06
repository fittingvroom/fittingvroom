package com.fittingvroom.ui.pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.databinding.FragmentModelBinding

import com.fittingvroom.databinding.FragmentPickUpBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

class PickUpFragment : Fragment() {
    private var viewBinding: FragmentPickUpBinding? = null
    private val navigation by lazy { findNavController() }
    private lateinit var viewModel: PickUpRvViewModel
    private val adapter: ViewPagerAdapter by lazy { ViewPagerAdapter(childFragmentManager) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPickUpBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.viewPager?.let {
            it.adapter = adapter
            viewBinding?.tabLayout?.setupWithViewPager(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupObservers()

    }
    private fun initViewModel() {
        val viewModel_: PickUpRvViewModel by viewModel()
        viewModel = viewModel_
    }

    private fun retrieveCategogy(data: List<Category>) {
        adapter.apply {
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun setupObservers() {
        viewModel.getCategorys().observe(viewLifecycleOwner, { it ->
            it?.let { result ->
                when (result) {
                    is AppState.Success -> {
                        viewBinding?.apply { //progressBar.visibility = View.GONE
                            }
                        result.data.let { categoty ->
                            //showSuccess(prducts)
                            retrieveCategogy(categoty)
                        }
                    }
                    is AppState.Error -> {
                       // showError(result)
                    }
                    is AppState.Loading -> {
                        //showLoading()
                    }
                }
            }


        })
    }

}