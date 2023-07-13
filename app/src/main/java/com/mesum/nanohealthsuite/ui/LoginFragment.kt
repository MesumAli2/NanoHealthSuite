package com.mesum.nanohealthsuite.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentLoginBinding
import okhttp3.ResponseBody

class LoginFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeners()

    }

    private fun listeners() {
        // Initialize views
        editTextEmail = binding.editTextEmail
        editTextPassword = binding.editTextPassword
        buttonLogin = binding.buttonLogin

        // Set click listener for the login button
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Perform login validation
            if (validateLogin(email, password)) {
                // Login successful
                    val hashMap = HashMap<String, String>()
                    hashMap["username"] = email ?: ""
                    hashMap["password"] = password ?: ""
                    val call = viewModel.apiService.login(hashMap)
                    call.enqueue(object : retrofit2.Callback<ResponseBody> {
                        override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                            if (response.code() == 200) {
                                // Handle successful login response
                                // For example, you can access the response body using response.body()
                                Log.d("responseRp", "success")
                                Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_allProductsFragment)

                            } else {
                                // Handle error response
                                // For example, you can access the error body using response.errorBody()
                                Log.e("responseRp", "${response.message().toString()}")
                                Toast.makeText(activity, "Incorrect User Name or Password!", Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                            // Handle network or unexpected error
                        }
                    })


                // Start your main activity or navigate to another screen

                // Finish the login activity
            } else {
                // Login failed
                Toast.makeText(activity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateLogin(email: String, password: String): Boolean {
        // Check if email and password are not empty
        return email.isNotEmpty() && password.isNotEmpty()
    }
}