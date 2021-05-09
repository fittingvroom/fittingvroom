package com.fittingvroom.ui.pick_up

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentProductCardBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.model.entitis.Product
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.viewmodel.ext.android.viewModel


class ProductCardFragment : Fragment() {

    companion object {
        fun newInstance() = ProductCardFragment()
        const val ID_PRODICT = "ID_PRODUCT"
        const val SIZE_PRODICT = "SIZE_PRODICT"

    }

    private var idProduct: Int = 0
    private val adapter: ProductImgAdapter by lazy { ProductImgAdapter(requireContext()) }
    private var viewBinding: FragmentProductCardBinding? = null
    private val navigation by lazy { findNavController() }

    private lateinit var viewModel: PickUpRvViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentProductCardBinding.inflate(inflater, container, false)
        val view = viewBinding?.root
        initViewModel()

        return view
    }

    private fun initViewModel() {

        val viewModel_: PickUpRvViewModel by viewModel()
        viewModel = viewModel_

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
        idProduct = arguments?.getInt(ID_PRODICT) ?: 0
        initToolbarNavigation()
        setupUI()
        setBtnListeners()
        setupObservers()
    }


    private fun setupUI() {
        viewBinding?.imageSwitcher?.let {
            it.adapter = adapter
            viewBinding?.dotsIndicator?.setViewPager(it)
        }

    }

    fun setBtnListeners() {
        val binding = viewBinding ?: return
        binding.ivCopy.setOnClickListener {
            val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("text", binding.tvVendorCode.text)
            clipboardManager.setPrimaryClip(clipData)

            MaterialAlertDialogBuilder(requireContext())
                .setMessage(resources.getString(R.string.copy_clipboard))
                .show()
        }
        binding.imgTryOn.setOnClickListener {
            if (isSizeSelected()) {
                val bundle = Bundle()
                bundle.putInt(ID_PRODICT, idProduct)
                bundle.putString(SIZE_PRODICT, binding.tvSize.text.toString())
                navigation.navigate(R.id.action_productCardFragment_to_navigation_fitting, bundle)
            } else {
                Toast.makeText(context, getString(R.string.no_size), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.imgBasket.setOnClickListener { }
        binding.btBasket.setOnClickListener {
            if (isSizeSelected()) {
            //************************
                Toast.makeText(context, "Сдесь должна быть корзина", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, getString(R.string.no_size), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.imgFavorites.setOnClickListener {
            //************************
            Toast.makeText(context, "Сдесь должно быть избранное", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun retrieveData(data: Product) {
        val binding = viewBinding ?: return
        binding.apply {
            tvName.text = data.name
            tvVendorCode.text = data.vendorCode
            tvPrice.text = data.price.toString()
            initSizeTextView(data.size)
            adapter.setData(data.img)
        }
    }

    private fun initSizeTextView(size: List<String>) {
        val binding = viewBinding ?: return
        val adapter = ArrayAdapter(requireContext(), R.layout.gender_list_item, size)
        binding.tvSize.apply { setAdapter(adapter) }

    }

    private fun setupObservers() {
        viewModel.getProduct(idProduct).observe(viewLifecycleOwner, { it ->
            it?.let { result ->
                when (result) {
                    is AppState.Success -> {
                        viewBinding?.apply { progressBar.visibility = View.GONE }
                        result.data.let { prduct ->
                            showSuccess(prduct)
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

    fun isSizeSelected(): Boolean {
        val binding = viewBinding ?: return false
        return !binding.tvSize.text.isNullOrEmpty()
    }

    private fun showSuccess(prduct: Product?) {
        if (prduct != null) {
            viewBinding?.apply {
                grData.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            retrieveData(prduct)

        } else {
            viewBinding?.apply {
                grData.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(result: AppState.Error) {
        viewBinding?.apply {
            progressBar.visibility = View.GONE
            grData.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        }
        Toast.makeText(context, result.error.localizedMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun showLoading() {
        viewBinding?.apply {
            progressBar.visibility = View.VISIBLE
            grData.visibility = View.GONE
            tvNoData.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }
}