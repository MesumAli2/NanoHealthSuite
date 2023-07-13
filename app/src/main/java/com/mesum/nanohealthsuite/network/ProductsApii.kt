package com.mesum.nanohealthsuite.network

import com.mesum.nanohealthsuite.model.ProductsRpItem

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://fakestoreapi.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    interface ProductsInterface {
        @GET("products")
        suspend fun getProducts() : List<ProductsRpItem>



    }

     object ProductsObj {
        val productsRequest = retrofit.create(ProductsInterface::class.java)
    }


