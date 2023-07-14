/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mesum.nanohealthsuite.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.ColorSpace
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.mesum.cryptoproject.ui.`interface`.OnProductClicked
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.ProductsItemBinding
import com.mesum.nanohealthsuite.model.ProductsRpItem


class ProductsAdapter (val onItemClick: OnProductClicked, val ctx : Context):
    ListAdapter<ProductsRpItem, ProductsAdapter.ProductViewHolder>(DiffCallback) {


    class ProductViewHolder(
        private var binding: ProductsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductsRpItem, ctx: Context) {
            binding.ivProduct.load(product.image)

            binding.ratingBar.rating = product.rating?.rate?.toFloat() ?: 2f
            binding.tvDesc.text = product.title.toString()
            binding.tvDescription.text = product.description.toString()

            Glide.with(ctx)
                .load(product.image) // Replace with your image URL or resource
                .centerCrop() // Scale type to crop and fill the ImageView
                .into(binding.ivProduct)
            val backgroundDrawable = binding.ivProduct.background
            val backgroundColor: Int = if (backgroundDrawable is ColorDrawable) {
                backgroundDrawable.color
            } else {
                // Handle the case when the background is not a solid color
                // You can set a default background color or perform additional logic here
                // For example, you can set a default color:
                Color.WHITE
            }

            val textColor: Int = if (isColorDark(backgroundColor)) {
                Color.WHITE // Set text color to white if background is dark
            } else {
                ContextCompat.getColor(ctx, R.color.price)
            }

            binding.txPrice.setTextColor(textColor)
            binding.txPrice.text = "${product.price.toString()} AED"


        }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5 // Adjust the threshold as needed
        }



    }



    companion object DiffCallback : DiffUtil.ItemCallback<ProductsRpItem>() {
        override fun areItemsTheSame(oldItem: ProductsRpItem, newItem: ProductsRpItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductsRpItem, newItem: ProductsRpItem): Boolean {
            return oldItem.image == newItem.image
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        return ProductViewHolder(
            ProductsItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, ctx)
        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(product.id.toString())
        }
    }
}
