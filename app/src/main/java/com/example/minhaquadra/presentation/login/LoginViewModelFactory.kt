package com.example.minhaquadra.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.domain.usercases.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.RegisterUsercase

class LoginViewModelFactory(
    private val getLoginUsercase: GetLoginUsercase,
    private val forgotPasswordUsercase: ForgotPasswordUsercase,
    private val registerUsercase: RegisterUsercase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(getLoginUsercase,forgotPasswordUsercase,registerUsercase) as T
    }


}