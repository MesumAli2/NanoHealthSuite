package com.mesum.nanohealthsuite.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NotificationCompat.getColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentLoginBinding
import com.mesum.nanohealthsuite.utils.AsteriskPasswordTransformationMethod
import okhttp3.ResponseBody

class LoginFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private var isShowPass = false
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
        requireActivity().window.statusBarColor = requireActivity().getColor(R.color.blue)
        listeners()

    }

    private fun listeners() {
        // Initialize views
        editTextEmail = binding.editTextEmail
        editTextPassword = binding.editTextPassword
        buttonLogin = binding.buttonLogin
       binding.tvShowHidePass.setOnClickListener {
           if (isShowPass) {
               binding.tvShowHidePass.setImageDrawable(context?.getDrawable(R.drawable.ic_show))
               binding.editTextPassword.transformationMethod = AsteriskPasswordTransformationMethod()
               isShowPass = false
           } else {
               binding.tvShowHidePass.setImageDrawable(context?.getDrawable(R.drawable.ic_hide))
               binding.editTextPassword.transformationMethod = null
               isShowPass = true
           }
       }


        val editTextEmail: EditText = binding.editTextEmail
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this implementation
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this implementation
            }

            override fun afterTextChanged(s: Editable?) {
                val email = s?.toString()
                if (email != null && email.length >= 7) {
                    // Email has more than 8 characters
                    // Perform your desired action here
                    binding.correctFormat.visibility = View.VISIBLE
                }else{
                    binding.correctFormat.visibility = View.GONE

                }
            }
        })
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
                binding.progressBar.visibility = View.VISIBLE

                    val call = viewModel.apiService.login(hashMap)
                    call.enqueue(object : retrofit2.Callback<ResponseBody> {
                        override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                            if (response.code() == 200) {
                                // Handle successful login response when credentials match below
                                //    "username": "mor_2314",
                                //    "password": "83r5^_"
                                //}
                                Log.d("responseRp", "success")
                                Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
                                binding.progressBar.visibility = View.GONE

                                findNavController().navigate(R.id.action_loginFragment_to_allProductsFragment)

                            } else {
                                // Handle error response
                                // For example, you can access the error body using response.errorBody()
                                Log.e("responseRp", "${response.message().toString()}")
                                binding.progressBar.visibility = View.GONE

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
                Toast.makeText(activity, "Enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validateLogin(email: String, password: String): Boolean {
        // Check if email and password are not empty
        return email.isNotEmpty() && password.isNotEmpty()
    }
}