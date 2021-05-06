package com.fittingvroom.ui.pick_up


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fittingvroom.R
import com.fittingvroom.databinding.FragmentPickUpBinding
import com.fittingvroom.model.AppState
import com.fittingvroom.model.entitis.Category
import org.koin.android.viewmodel.ext.android.viewModel

class PickUpFragment : Fragment() {
    private var viewBinding: FragmentPickUpBinding? = null
    private val navigation by lazy { findNavController() }
    private lateinit var viewModel: PickUpRvViewModel
    private val adapter: ViewPagerAdapter by lazy { ViewPagerAdapter(childFragmentManager) }
    private var data: List<Category> = arrayListOf()
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
        initToolbarNavigation()
    }


    private fun setCustomTab() {
        val layoutInflater = LayoutInflater.from(context)
        val tabs = viewBinding?.tabLayout

        if (tabs != null)
            for (i in 0 until tabs.tabCount) {
                //берем разметку для таба
                val tab = layoutInflater.inflate(R.layout.item_tab, tabs, false)
                tab.findViewById<AppCompatTextView>(R.id.tv_name)
                    .text = data[i].name
                Glide.with(tab.context)
                    .load(Uri.parse(data[i].img))
                    .circleCrop()
                    .fitCenter()
                    .into(tab.findViewById(R.id.iv_img))
                tabs.getTabAt(i)?.customView = tab
            }
    }

    private fun initToolbarNavigation() {
        val binding = viewBinding ?: return
        binding.toolbar.setNavigationIcon(R.drawable.ic_toolbar_back_btn)
        binding.toolbar.setNavigationOnClickListener {
            navigation.popBackStack()
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
        setCustomTab()
    }

    private fun setupObservers() {
        viewModel.getCategorys().observe(viewLifecycleOwner, { it ->
            it?.let { result ->
                when (result) {
                    is AppState.Success -> {
                        viewBinding?.apply {
                            progressBar.visibility = View.GONE
                        }
                        result.data.let { categoty ->
                            showSuccess(categoty)
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

    private fun showSuccess(categoty: List<Category>?) {
        if (categoty != null && categoty.isNotEmpty()) {
            viewBinding?.apply {
                viewPager.visibility = View.VISIBLE
                tvNoData.visibility = View.GONE
            }
            data = categoty
            retrieveCategogy(categoty)

        } else {
            viewBinding?.apply {
                viewPager.visibility = View.GONE
                tvNoData.visibility = View.VISIBLE
            }
        }
    }

    private fun showError(result: AppState.Error) {
        viewBinding?.apply {
            progressBar.visibility = View.GONE
            viewPager.visibility = View.GONE
            tvNoData.visibility = View.VISIBLE
        }
        Toast.makeText(context, result.error.localizedMessage, Toast.LENGTH_LONG)
            .show()
    }

    private fun showLoading() {
        viewBinding?.apply {
            progressBar.visibility = View.VISIBLE
            viewPager.visibility = View.GONE
            tvNoData.visibility = View.GONE
        }
    }
}