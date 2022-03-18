package com.example.minhaquadra.presentation.login

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
import com.example.minhaquadra.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar


class ForgotPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgotPasswordBinding
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false)

        viewModel = (activity as LoginActivity).loginViewModel

        binding.txtEmail.doOnTextChanged { text, start, before, count -> viewModel.enableForgotPasswordButton(text.toString())  }

        viewModel.enableForgotPasswordButton.observe(requireActivity(), Observer {
            binding.btnRecoverPassword.isEnabled = it
        })

        viewModel.forgotPasword.observe(this, Observer { response ->
            when(response){
                is Resource.Loading ->{
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Error ->{
                    binding.progress.visibility = View.GONE
                    Snackbar.make(requireView(), response.message.toString(),Snackbar.LENGTH_LONG).show()

                }
                is Resource.Success ->{
                    binding.progress.visibility = View.GONE
                    if (response.data!!){
                        Snackbar.make(requireView(), "Consulte sua caixa de email para redefinir a senha",Snackbar.LENGTH_LONG).show()
                    }else{
                        Snackbar.make(requireView(), "Algo de errado aconteceu ao enviar o email, verifique se é o email correto",Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

        binding.btnRecoverPassword.setOnClickListener {
            viewModel.forgotPassword(binding.txtEmail.text.toString())
        }


        return binding.root
    }


}