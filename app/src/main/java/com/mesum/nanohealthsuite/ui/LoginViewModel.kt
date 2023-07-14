package com.mesum.nanohealthsuite.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mesum.nanohealthsuite.network.LoginApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel : ViewModel() {

    val apiService: LoginApiInterface

    init {

        val client = OkHttpClient.Builder()
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiService = retrofit.create(LoginApiInterface::class.java)
    }


}
