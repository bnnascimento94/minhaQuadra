package com.example.minhaquadra.domain.usercases.login

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.LoginRepository

class ForgotPasswordUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(email:String):Resource<Boolean>? = loginRepository.forgotPassword(email)
}