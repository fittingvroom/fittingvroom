package com.fittingvroom.ui.cart

import androidx.recyclerview.widget.DiffUtil

class CartDataDiffCallback(val newData: List<CartData>, val oldData: List<CartData>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newData[newItemPosition].id == oldData[oldItemPosition].id && newData[newItemPosition].size == oldData[oldItemPosition].size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newData[newItemPosition] == oldData[oldItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}