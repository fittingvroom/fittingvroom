package com.fittingvroom.ui.pick_up

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide


class ProductImgAdapter(val context: Context) : PagerAdapter() {
    private var data: List<String> = arrayListOf()

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()

    }

    override fun getCount() = data.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        Glide.with(container)
            .load(Uri.parse(data[position]))
            .fitCenter()
            .into(imageView)
        container.addView(imageView)
        return imageView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}