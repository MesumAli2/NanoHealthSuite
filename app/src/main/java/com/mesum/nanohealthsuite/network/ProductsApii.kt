package com.mesum.nanohealthsuite.network

import com.mesum.nanohealthsuite.model.ProductsRpItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL ="https://fakestoreapi.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    interface ProductsInterface {
        @GET("products")
        suspend fun getProducts() : List<ProductsRpItem>

        @GET("products/{id}")
        suspend fun getProduct(@Path("id") productId: String): ProductsRpItem

    }

     object ProductsObj {
        val productsRequest = retrofit.create(ProductsInterface::class.java)
    }


