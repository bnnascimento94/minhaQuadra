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
    var loginField: String? = null


    //login
    var emailRegister: String? = null

    //login
    var passwordRegister: String? = null


    //btnRegister
    val enableBtnRegister: MutableLiveData<Boolean> = MutableLiveData()

    val userRegistered: MutableLiveData<Resource<User>> = MutableLiveData()


    fun loginUserData(username:String, password: String) = viewModelScope.launch {
        loginUserData.postValue(getLoginUsercase.execute(username,password))
    }

    fun forgotPassword(email:String) = viewModelScope.launch {
        forgotPasword.postValue(Resource.Loading())
        forgotPasword.postValue(forgotPasswordUsercase.execute(email))
    }

    fun enableForgotPasswordButton(email: String){
        enableForgotPasswordButton.value = email.contains("@")
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
        enableLoginButtonLogin.value = loginField != null &&
                loginField!!.contains("@") &&
                password.isNotEmpty()
    }


    fun enableRegisterButton(passwordConfirmed: String){
        enableLoginButtonLogin.value = emailRegister != null &&
                passwordRegister != null &&
                emailRegister!!.contains("@") &&
                passwordRegister!!.isNotEmpty() &&
                passwordRegister.equals(passwordConfirmed)
    }

    fun registerUser() = viewModelScope.launch {
        userRegistered.postValue(registerUsercase.execute(emailRegister!!,passwordRegister!!))
    }



}