package com.ferdinand.habittracker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ferdinand.habittracker.R
import com.ferdinand.habittracker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val validUsername = "student"
    private val validPassword = "123"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtError.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            checkLogin(it)
        }
    }

    private fun checkLogin(view: View) {
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        if (username == validUsername && password == validPassword) {
            binding.txtError.visibility = View.GONE
            view.findNavController().navigate(R.id.action_login_to_dashboard)
        } else {
            binding.txtError.visibility = View.VISIBLE
        }
    }
}