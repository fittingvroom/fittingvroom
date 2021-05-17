package com.fittingvroom.ui.cart

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fittingvroom.databinding.ShopcaptItemBinding


class CatrAdapter(
    private var onDelete: OnListItemClickListener,
    private var onFavotite: OnListItemClickListener,
    private var onAmount: OnItemSelectedListener
) :
    RecyclerView.Adapter<CatrAdapter.RecyclerItemViewHolder>() {

    private lateinit var binding: ShopcaptItemBinding
    val dataCart: MutableList<CartData> = mutableListOf()


    fun setData(newData: List<CartData>) {
        val diffResult = DiffUtil.calculateDiff(CartDataDiffCallback(newData, dataCart))
        dataCart.clear()
        dataCart.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        binding = ShopcaptItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(dataCart[position])
    }

    override fun getItemCount(): Int {
        return dataCart.size
    }

    inner class RecyclerItemViewHolder(val binding: ShopcaptItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CartData) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvName.text = data.name
                binding.tvVendorCode.text = data.vendorCode
                binding.tvColor.text = data.color
                binding.tvPrice.text = data.price.toString()
                binding.tvTotal.text = data.total.toString()
                binding.tvSize.text = data.size
                if (data.favorite) {
                    binding.imgFavoritesOn.visibility = View.VISIBLE
                    binding.imgFavorites.visibility = View.INVISIBLE
                } else {
                    binding.imgFavoritesOn.visibility = View.INVISIBLE
                    binding.imgFavorites.visibility = View.VISIBLE
                }
                Glide.with(binding.imageView.context)
                    .load(
                        Uri.parse(data.img)
                    )
                    .fitCenter()
                    .into(binding.imageView)

                binding.imgFavorites.setOnClickListener { onFavotite.onItemClick(data) }
                binding.imgFavoritesOn.setOnClickListener { onFavotite.onItemClick(data) }
                binding.imgDelete.setOnClickListener { onDelete.onItemClick(data) }
                binding.tvAmount.setSelection(data.amount - 1)
                binding.tvAmount.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (data.amount != position + 1)
                            onAmount.onItemSelected(data, position + 1)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }


                }
            }
        }
    }
}

interface OnListItemClickListener {
    fun onItemClick(data: CartData)
}

interface OnItemSelectedListener {
    fun onItemSelected(data: CartData, position: Int)
}