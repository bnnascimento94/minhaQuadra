package com.example.minhaquadra.domain.usercases

import com.example.minhaquadra.data.model.Login
import com.example.minhaquadra.domain.repository.LoginRepository

class RegisterUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(login: Login):Login? = loginRepository.registerUser(login)
}