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
import com.fittingvroom.model.entitis.Product
import org.koin.android.viewmodel.ext.android.viewModel


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private var viewBinding: FragmentShopcaptBinding? = null
    private val navigation by lazy { findNavController() }


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
        //setupUI()
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

    private fun showSuccess(prduct: Product?) {
        if (prduct != null) {
            viewBinding?.apply {
                data.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            //retrieveData(prduct)

        } else {
            viewBinding?.apply {
                data.visibility = View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}