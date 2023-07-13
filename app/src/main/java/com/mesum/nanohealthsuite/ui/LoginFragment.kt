package com.mesum.nanohealthsuite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!


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
                Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()

                // Start your main activity or navigate to another screen
                findNavController().navigate(R.id.action_loginFragment_to_allProductsFragment)

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