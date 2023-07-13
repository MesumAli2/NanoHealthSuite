package com.mesum.nanohealthsuite.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.nanohealthsuite.model.ProductsRp
import com.mesum.nanohealthsuite.model.ProductsRpItem
import com.mesum.nanohealthsuite.network.ProductsObj
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    val productsResponse : MutableLiveData<List<ProductsRpItem>> = MutableLiveData<List<ProductsRpItem>>()
    val productResponse : MutableLiveData<ProductsRpItem> = MutableLiveData<ProductsRpItem>()


    fun getProducts() {
        viewModelScope.launch {
            val products = ProductsObj.productsRequest.getProducts()
            try {
                productsResponse.value = products
            }catch (e : java.lang.Exception){
                Log.d("exceptionFound", e.toString())
            }
        }
    }

    fun getProduct(id : String) {
        viewModelScope.launch {
            try {
                val product = ProductsObj.productsRequest.getProduct(productId = id)
                productResponse.value = product
            }catch (e : java.lang.Exception){
                Log.d("exceptionFound", e.toString())
            }
        }
    }

}