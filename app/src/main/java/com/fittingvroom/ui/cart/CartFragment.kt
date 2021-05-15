package com.fittingvroom.ui.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentShopcaptBinding
import com.fittingvroom.model.AppState

import org.koin.android.viewmodel.ext.android.viewModel


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private var viewBinding: FragmentShopcaptBinding? = null
    private val navigation by lazy { findNavController() }
    private val adapter: CatrAdapter by lazy { CatrAdapter(onDelete, onFavotite, onAmount) }
    val d = mutableListOf(
        CartData(
            1,
            1,
            "dfsgfs",
            "dtgsg",
            1000f,
            "hgfghf",
            "",
            "XL",
            "file:///android_asset/Jeans/j4.png",
            0f,
            1,
            false
        ), CartData(
            1,
            1,
            "dfsgfs",
            "dtgsg",
            1000f,
            "hgfghf",
            "",
            "XL",
            "file:///android_asset/Jeans/j3.png",
            0f,
            1,
            false
        )
    )

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
        viewBinding?.data?.setHasFixedSize(true)

        showSuccess(
            d
        )

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
        //setupObservers()
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
            notifyDataSetChanged()
        }
    }

    private fun showSuccess(data: List<CartData>?) {

        if (data != null) {
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

    private fun showError(result: AppState.Error) {
        viewBinding?.apply {
            progressBar.visibility = View.GONE
            data.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        }
        Toast.makeText(context, result.error.localizedMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun showLoading() {
        viewBinding?.apply {
            progressBar.visibility = View.VISIBLE
            data.visibility = View.GONE
            tvNoData.visibility = View.GONE
        }
    }

    private val onDelete: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: CartData) {
                adapter.apply {
                    val index = d.indexOf(data)
                    d.remove(data)
                    deleteData(d, index)
                }
                Toast.makeText(context, "onDelete", Toast.LENGTH_SHORT).show()
            }
        }
    private val onFavotite: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(data: CartData) {
                adapter.apply {
                    val index = d.indexOf(data)
                    d[index].favorite = !d[index].favorite
                    updateData(d, index)
                }

            }
        }
    private val onAmount: OnItemSelectedListener =
        object : OnItemSelectedListener {
            override fun onItemSelected(data: CartData, position: Int) {
                adapter.apply {
                    val index = d.indexOf(data)
                    d[index].amount = position
                    d[index].total=d[index].price*d[index].amount
                    updateData(d, index)
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}