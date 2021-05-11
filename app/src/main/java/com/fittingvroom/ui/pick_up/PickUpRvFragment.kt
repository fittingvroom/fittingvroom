package com.fittingvroom.ui.pick_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentPickUpRvBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.model.entitis.Product
import com.fittingvroom.ui.pick_up.ProductCardFragment.Companion.ID_PRODICT
import org.koin.android.viewmodel.ext.android.viewModel

class PickUpRvFragment : Fragment() {

    companion object {
        fun newInstance() = PickUpRvFragment()
        const val ID_CATEGORY = "ID_CATEGORY"
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
        idCategory = arguments?.getInt(ID_CATEGORY) ?: 0
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()

    }
    private fun initViewModel() {

        val viewModel_: PickUpRvViewModel by viewModel()
        viewModel = viewModel_

    }



    private fun setupUI() {
        viewBinding?.recyclerview?.adapter = adapter
        viewBinding?.recyclerview?.setHasFixedSize(true)
    }

    private fun retrieveProducts(data: List<Product>) {
        adapter.apply {
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun setupObservers() {
        viewModel.getProducts(idCategory).observe(viewLifecycleOwner, { it ->
            it?.let { result ->
                when (result) {
                    is AppState.Success -> {
                        viewBinding?.apply { progressBar.visibility = View.GONE }
                        result.data.let { prducts ->
                            showSuccess(prducts)
                        }
                    }
                    is AppState.Error -> {
                        showError(result)
                    }
                    is AppState.Loading -> {
                        showLoading()
                    }
                }
            }


        })
    }

    private fun showSuccess(prducts: List<Product>?) {
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

    private fun showError(result: AppState.Error) {
        viewBinding?.apply {
            progressBar.visibility = View.GONE
            recyclerview.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        }
        Toast.makeText(context, result.error.localizedMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun showLoading() {
        viewBinding?.apply {
            progressBar.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
            tvNoData.visibility = View.GONE
        }
    }

    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: Product) {
                val bundle = Bundle()
                bundle.putInt(ID_PRODICT, data.id)
                navigation.navigate(R.id.productCardFragment,bundle)
            }
        }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}