package com.example.minhaquadra.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentRegisterBinding
import com.example.minhaquadra.presentation.home.HomeActivity
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment() {

    lateinit var binding: FragmentRegisterBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewModel = (activity as LoginActivity).loginViewModel

        binding.txtEmail.doOnTextChanged { text, start, before, count -> viewModel.emailRegister = text.toString()  }

        binding.txtPassword.doOnTextChanged { text, start, before, count -> viewModel.passwordRegister = text.toString()  }

        binding.txtConfirmPassword.doOnTextChanged { text, start, before, count -> viewModel.enableRegisterButton(text.toString())  }

        viewModel.enableBtnRegister.observe(this, Observer {
            binding.btnRealizarRegistro.isEnabled = it
        })

        binding.btnRealizarRegistro.setOnClickListener {
            viewModel.registerUser()
        }

        viewModel.userRegistered.observe(requireActivity(), Observer { response ->
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
                    startActivity(Intent(activity, HomeActivity::class.java))
                }
            }
        })

        return binding.root
    }


}