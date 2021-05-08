package com.fittingvroom.ui.pick_up

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fittingvroom.databinding.RvPickupItemBinding
import com.fittingvroom.model.entitis.Product

class PickUpAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<PickUpAdapter.RecyclerItemViewHolder>() {

    private lateinit var binding: RvPickupItemBinding
    private var data: List<Product> = arrayListOf()

    fun setData(data: List<Product>) {
        this.data = data
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        binding = RvPickupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(val binding: RvPickupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Product) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.tvName.text = data.name
                binding.tvColor.text = data.color
                binding.tvPrice.text = data.price.toString()

                Glide.with(binding.imageView.context)
                    .load(
                        Uri.parse(data.img.getOrElse(0) { "" })
                    )
                    .fitCenter()
                    .into(binding.imageView)
                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }
    }
}

interface OnListItemClickListener {
    fun onItemClick(data: Product)
}