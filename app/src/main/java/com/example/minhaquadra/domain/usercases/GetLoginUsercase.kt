package com.example.minhaquadra.domain.usercases

import com.example.minhaquadra.data.model.Login
import com.example.minhaquadra.domain.repository.LoginRepository

class GetLoginUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(userName:String,password:String): Login? = loginRepository.getLogin(userName,password)
}