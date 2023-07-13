package com.mesum.nanohealthsuite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentAllProductsBinding
import com.mesum.nanohealthsuite.databinding.FragmentDetailsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productsAdapter : ProductsAdapter
    private val viewModel : ProductsViewModel by viewModels()
    private var idArgument: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idArgument = it.getString("productId");

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        viewModel.productResponse.observe(viewLifecycleOwner){
            Log.d("singleProduct", it.toString())
        }
    }

    private fun initialize() {
        viewModel.getProduct(idArgument.toString())
    }

}