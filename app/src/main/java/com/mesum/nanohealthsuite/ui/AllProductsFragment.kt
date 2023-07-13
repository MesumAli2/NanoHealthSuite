package com.mesum.nanohealthsuite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mesum.cryptoproject.ui.`interface`.OnProductClicked
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentAllProductsBinding
import com.mesum.nanohealthsuite.databinding.FragmentLoginBinding

class AllProductsFragment : Fragment(), OnProductClicked {
    private var _binding : FragmentAllProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsAdapter : ProductsAdapter
    private val viewModel : ProductsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

        viewModel.productsResponse.observe(viewLifecycleOwner){
            productsAdapter.submitList(it)

            binding.rvProducts.adapter = productsAdapter

        }

    }
    private fun initialize() {
        binding.toolBar.setTitle("All Products")
        viewModel.getProducts()
        productsAdapter = ProductsAdapter(this, requireContext())

    }

    override fun onItemClick(id: String) {
        val bundle = bundleOf("productId" to id,)
        findNavController().navigate(R.id.action_allProductsFragment_to_detailsFragment, bundle)    }


}