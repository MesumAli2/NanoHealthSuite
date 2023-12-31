package com.mesum.nanohealthsuite.network

import com.mesum.nanohealthsuite.model.ProductsRpItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL ="https://fakestoreapi.com/"

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
    interface LoginApiInterface {
        @FormUrlEncoded
        @POST("auth/login")
        fun login(@FieldMap hashMap: HashMap<String, String>): Call<ResponseBody>
    }

     object ProductsObj {
        val productsRequest = retrofit.create(ProductsInterface::class.java)
    }


