package com.fittingvroom.ui.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentShopcaptBinding
import org.koin.android.viewmodel.ext.android.viewModel


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private var viewBinding: FragmentShopcaptBinding? = null
    private val navigation by lazy { findNavController() }
    private val adapter: CatrAdapter by lazy { CatrAdapter(onDelete, onFavotite, onAmount) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentShopcaptBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()
        return view
    }

    private fun setupUI() {
        viewBinding?.data?.adapter = adapter

    }

    private fun initViewModel() {
        val viewModel_: CartViewModel by viewModel()
        cartViewModel = viewModel_
    }


    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.toolbar.setNavigationOnClickListener {
            navigation.popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation()
        setupUI()
        setBtnListeners()
        setupObservers()
        cartViewModel.getBasket()

    }

    private fun setupObservers() {
        cartViewModel.data.observe(viewLifecycleOwner, {
            it?.let { result ->
                showSuccess(result)
            }
        })
        cartViewModel.total.observe(viewLifecycleOwner, {
            viewBinding?.tvPrice?.text = it
        })

    }

    fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.btGo.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.hm.com/"))
            startActivity(browserIntent)
        }
    }

    private fun retrieveData(data: List<CartData>) {
        adapter.apply {
            setData(data)
        }
    }

    private fun showSuccess(data: List<CartData>?) {
        if (!data.isNullOrEmpty()) {
            viewBinding?.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            retrieveData(data)
        } else {
            viewBinding?.apply {
                progressBar.visibility = View.GONE
                this.data.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private val onDelete: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: CartData) {
                cartViewModel.deleteBasket(data)
            }
        }
    private val onFavotite: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: CartData) {
                cartViewModel.favoriteBasket(data)
            }
        }
    private val onAmount: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(data: CartData, position: Int) {
                cartViewModel.amountBasket(data, position)
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}