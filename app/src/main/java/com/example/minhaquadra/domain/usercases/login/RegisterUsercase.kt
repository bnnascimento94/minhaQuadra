package com.example.minhaquadra.domain.usercases.login

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.LoginRepository

class RegisterUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(username:String,password:String): Resource<User>? = loginRepository.registerUser(username, password)
}