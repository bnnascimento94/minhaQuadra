package com.example.minhaquadra.presentation.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.usercases.VerifyUserUsercase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val verifyUserUsercase: VerifyUserUsercase): ViewModel() {

    //register
    val connectedUser: MutableLiveData<Resource<User>> = MutableLiveData()
    val splashTimer: MutableLiveData<Boolean> = MutableLiveData()

    fun verifyConnectedUser() = viewModelScope.launch {
        connectedUser.postValue(verifyUserUsercase.execute())
    }

    fun setSplashTimer() = viewModelScope.launch {
        delay(5000)
        splashTimer.postValue(true)
    }

}