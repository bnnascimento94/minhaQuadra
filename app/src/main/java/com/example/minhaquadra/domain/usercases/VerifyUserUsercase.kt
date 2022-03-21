package com.example.minhaquadra.domain.usercases

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.LoginRepository

class VerifyUserUsercase(private val loginRepository: LoginRepository) {

    suspend fun execute(): Resource<User>? = loginRepository.getUserConnected()

}