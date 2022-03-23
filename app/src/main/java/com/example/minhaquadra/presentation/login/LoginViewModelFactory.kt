package com.example.minhaquadra.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.domain.usercases.login.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.login.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.login.RegisterUsercase

class LoginViewModelFactory(
    private val getLoginUsercase: GetLoginUsercase,
    private val forgotPasswordUsercase: ForgotPasswordUsercase,
    private val registerUsercase: RegisterUsercase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(getLoginUsercase,forgotPasswordUsercase,registerUsercase) as T
    }


}