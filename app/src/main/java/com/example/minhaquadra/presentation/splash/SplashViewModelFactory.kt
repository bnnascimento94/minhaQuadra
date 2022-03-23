package com.example.minhaquadra.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.domain.usercases.login.VerifyUserUsercase

class SplashViewModelFactory(
    private val verifyUserUsercase: VerifyUserUsercase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(verifyUserUsercase) as T
    }


}