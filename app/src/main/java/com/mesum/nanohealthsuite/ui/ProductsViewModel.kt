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

}