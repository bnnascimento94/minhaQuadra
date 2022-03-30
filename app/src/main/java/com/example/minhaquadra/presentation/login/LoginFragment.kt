package com.example.minhaquadra.presentation.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.presentation.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var viewModel: LoginViewModel
    lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var preferencias: Preferencias

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel = (activity as LoginActivity).loginViewModel

        binding.txtEmail.doOnTextChanged { text, start, before, count -> viewModel.enableLoginForgotPasswordButton(text.toString())  }

        binding.txtPassword.doOnTextChanged { text, start, before, count -> viewModel.enableLoginButton(text.toString())  }

        
        viewModel.enableLoginButtonLogin.observe(requireActivity(), Observer {
            binding.btnEntrar.isEnabled = it
        })

        viewModel.enableLoginButtonForgotPassword.observe(requireActivity(), Observer {
            binding.btnEsqueciASenha.isEnabled = it
        })

        binding.btnEntrar.setOnClickListener {
            viewModel.loginUserData(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
        }

        binding.btnEsqueciASenha.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.btnCadastrar.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.loginUserData.observe(requireActivity(), Observer { response ->
            when(response){
                is Resource.Loading ->{
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Error ->{
                    binding.progress.visibility = View.GONE
                    Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_LONG).show()

                }
                is Resource.Success ->{
                    binding.progress.visibility = View.GONE
                    preferencias.salvarDados(response.data!!)
                    startActivity(Intent(activity, HomeActivity::class.java))
                }
            }
        })


        return binding.root
    }

}