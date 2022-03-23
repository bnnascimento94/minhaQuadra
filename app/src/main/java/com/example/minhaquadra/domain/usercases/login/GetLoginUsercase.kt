package com.example.minhaquadra.domain.usercases.login

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.LoginRepository

class GetLoginUsercase(private val loginRepository: LoginRepository) {
    suspend fun execute(userName:String,password:String): Resource<User>? = loginRepository.getLogin(userName,password)
}