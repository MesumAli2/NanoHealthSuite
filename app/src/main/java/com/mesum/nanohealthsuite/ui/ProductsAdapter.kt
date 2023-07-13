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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mesum.nanohealthsuite.databinding.ProductsItemBinding
import com.mesum.nanohealthsuite.model.ProductsRpItem


class ProductsAdapter :
    ListAdapter<ProductsRpItem, ProductsAdapter.ProductViewHolder>(DiffCallback) {


    class ProductViewHolder(
        private var binding: ProductsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductsRpItem) {
            binding.ivProduct.load(product.image)
            binding.txPrice.text = product.price.toString()
            binding.ratingBar.rating = product.rating

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

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}
