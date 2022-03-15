package com.example.minhaquadra.data.repository.login

import com.example.minhaquadra.data.model.Login
import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource): LoginRepository {

    override suspend fun getLogin(username: String, password: String): Login? {
        return loginDataSource.buscarLogin(username,password)
    }

    override suspend fun registerUser(login: Login): Login? {
        return loginDataSource.registerUser(login)
    }

    override suspend fun forgotPassword(email: String, password: String): Login? {
        return loginDataSource.forgotPassword(email,password)
    }
}