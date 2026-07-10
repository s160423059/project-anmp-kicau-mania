package com.ferdinand.habittracker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.ferdinand.habittracker.R
import com.ferdinand.habittracker.databinding.FragmentLoginBinding
import com.ferdinand.habittracker.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    private val prefsName = "habit_tracker_prefs"
    private val keyIsLoggedIn = "is_logged_in"

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

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        if (isSessionLoggedIn()) {
            view.findNavController().navigate(R.id.action_login_to_dashboard)
            return
        }

        binding.txtError.visibility = View.GONE

        binding.btnLogin.setOnClickListener {
            checkLogin()
        }

        observeViewModel()
    }

    private fun checkLogin() {
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        binding.txtError.visibility = View.GONE
        viewModel.login(username, password)
    }

    private fun observeViewModel() {
        viewModel.loginResultLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                saveSessionLoggedIn()
                binding.txtError.visibility = View.GONE
                view?.findNavController()?.navigate(R.id.action_login_to_dashboard)
            } else {
                binding.txtError.visibility = View.VISIBLE
            }
        }
    }

    private fun isSessionLoggedIn(): Boolean {
        val prefs = requireActivity().getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        return prefs.getBoolean(keyIsLoggedIn, false)
    }

    private fun saveSessionLoggedIn() {
        val prefs = requireActivity().getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(keyIsLoggedIn, true).apply()
    }
}