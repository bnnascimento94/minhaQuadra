package com.example.minhaquadra.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.usercases.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.RegisterUsercase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getLoginUsercase: GetLoginUsercase,
    private val forgotPasswordUsercase: ForgotPasswordUsercase,
    private val registerUsercase: RegisterUsercase
): ViewModel() {

    //register
    val registerUserData: MutableLiveData<Resource<User>> = MutableLiveData()

    //login
    val loginUserData: MutableLiveData<Resource<User>> = MutableLiveData()

    //forgot password
    val forgotPasword: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    //enableForgotPasswordButton
    val enableForgotPasswordButton: MutableLiveData<Boolean> = MutableLiveData()

    //enable button forgotpassword
    val enableLoginButtonForgotPassword: MutableLiveData<Boolean> = MutableLiveData()

    //enable button forgotpassword
    val enableLoginButtonLogin: MutableLiveData<Boolean> = MutableLiveData()

    //login
    val loginField: String


    fun registerUser(username:String, password: String)=  viewModelScope.launch {
        registerUserData.postValue(registerUsercase.execute(username, password))
    }

    fun loginUserData(username:String, password: String) = viewModelScope.launch {
        loginUserData.postValue(getLoginUsercase.execute(username,password))
    }

    fun forgotPassword(email:String) = viewModelScope.launch {
        forgotPasword.postValue(Resource.Loading())
        forgotPasword.postValue(forgotPasswordUsercase.execute(email))
    }

    fun enableForgotPasswordButton(email: String){
        if(email.contains("@")){
            enableForgotPasswordButton.value = true
        }else{
            enableForgotPasswordButton.value = false
        }
    }

    fun enableLoginForgotPasswordButton(email: String){
        if(email.contains("@")){
            loginField = email
            enableLoginButtonForgotPassword.value = true
        }else{
            enableLoginButtonForgotPassword.value = false
        }
    }

    fun enableLoginButton(password: String){
        if(loginField != null && loginField.contains("@") && password.length > 0){
            enableLoginButtonLogin.value = true
        }else{
            enableLoginButtonLogin.value = false
        }
    }





}