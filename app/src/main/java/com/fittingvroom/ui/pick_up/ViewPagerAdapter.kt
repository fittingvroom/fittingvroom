package com.fittingvroom.ui.pick_up

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


//class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PageHolder>() {
//    val category = listOf("0", "1", "2")
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        PageHolder(
//            LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_tab, parent, false) as View
//        )
//
//    fun getTabView(position: Int): View? {
//        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
//        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.custom_tab, null)
//        val tv = v.findViewById<View>(R.id.textView) as TextView
//        tv.setText(tabTitles.get(position))
//        val img: ImageView = v.findViewById<View>(R.id.imgView) as ImageView
//        img.setImageResource(imageResId.get(position))
//        return v
//    }
//
//    override fun getItemCount() = category.count()
//    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
//        (holder.view as? TextView)?.also {
//            it.text = "Page " + category.get(position)
//
//            val backgroundColorResId = if (position % 2 == 0) R.color.blue else R.color.orange)
//            it.setBackgroundColor(ContextCompat.getColor(it.context, backgroundColorResId))
//        }
//    }
//
//    inner class PageHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = view.textView
//    }
//}