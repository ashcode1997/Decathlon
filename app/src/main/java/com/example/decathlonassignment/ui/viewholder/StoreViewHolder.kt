package com.example.decathlonassignment.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.decathlonassignment.databinding.ItemLayoutBinding
import com.example.decathlonassignment.model.Item

class StoreViewHolder(
    private val binding: ItemLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        binding.apply {
            binding.name.text = item.name
            binding.price.text = item.price.toString()
            binding.brand.text = item.brand
            Glide
                .with(binding.root.context)
                .load(item.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.image)

        }
    }
}
