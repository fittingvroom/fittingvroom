package com.fittingvroom.ui.pick_up

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.fittingvroom.model.entitis.Category
import com.fittingvroom.model.entitis.Product

class ViewPagerAdapter( fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var data: List<Category> = arrayListOf()

    fun setData(data: List<Category>) {
        this.data = data
        notifyDataSetChanged()

    }
    fun createFragment(position: Int): Fragment = PickUpRvFragment().apply {
        arguments = bundleOf(
            "ID_CATEGORY" to data[position].id
        )
    }

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Fragment {
        return createFragment(position)
    }

    override fun getPageTitle(position: Int)=data[position].name

}


