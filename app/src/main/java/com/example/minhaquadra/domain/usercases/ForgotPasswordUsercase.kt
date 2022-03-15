package com.example.minhaquadra.domain.usercases

import com.example.minhaquadra.data.model.Login
import com.example.minhaquadra.domain.repository.LoginRepository

class ForgotPasswordUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(email:String, password:String):Login? = loginRepository.forgotPassword(email,password)
}