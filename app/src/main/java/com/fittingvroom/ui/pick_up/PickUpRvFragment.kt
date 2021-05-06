package com.fittingvroom.ui.pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.databinding.FragmentPickUpRvBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.model.entitis.Product
import org.koin.android.viewmodel.ext.android.viewModel

class PickUpRvFragment : Fragment() {

    companion object {
        fun newInstance() = PickUpRvFragment()
    }

    private var idCategory: Int = 0
    private val adapter: PickUpAdapter by lazy { PickUpAdapter(onListItemClickListener) }
    private var viewBinding: FragmentPickUpRvBinding? = null
    private val navigation by lazy { findNavController() }

    private lateinit var viewModel: PickUpRvViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPickUpRvBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        idCategory = arguments?.getInt("ID_CATEGORY") ?: 0
        return view
    }

    private fun initViewModel() {

        val viewModel_: PickUpRvViewModel by viewModel()
        viewModel = viewModel_

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupUI()
        setupObservers()

    }

    private fun setupUI() {
        viewBinding?.recyclerview?.adapter = adapter
    }

    private fun retrieveProducts(data: List<Product>) {
        adapter.apply {
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun setupObservers() {
        viewModel.getProduct(idCategory).observe(viewLifecycleOwner, { it ->
            it?.let { result ->
                when (result) {
                    is AppState.Success -> {
                        viewBinding?.apply { progressBar.visibility = View.GONE }
                        result.data.let { prducts ->
                            if (prducts != null && prducts.isNotEmpty()) {
                                viewBinding?.apply {
                                    recyclerview.visibility = View.VISIBLE
                                    tvNoData.visibility = View.GONE
                                }
                                retrieveProducts(prducts)

                            } else {
                                viewBinding?.apply {
                                    recyclerview.visibility = View.GONE
                                    tvNoData.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                    is AppState.Error -> {
                        viewBinding?.apply {
                            progressBar.visibility = View.GONE
                            recyclerview.visibility = View.GONE
                            tvNoData.visibility = View.VISIBLE
                        }
                        Toast.makeText(context, result.error.localizedMessage, Toast.LENGTH_LONG)
                            .show()
                    }
                    is AppState.Loading -> {
                        viewBinding?.apply {
                            progressBar.visibility = View.VISIBLE
                            recyclerview.visibility = View.GONE
                            tvNoData.visibility = View.GONE
                        }
                    }
                }
            }


        })
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: Product) {
                Toast.makeText(context, data.name, Toast.LENGTH_SHORT).show()
            }
        }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}