package com.example.minhaquadra.data.repository.login

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource): LoginRepository {
    override suspend fun getUserConnected(): Resource<User>? {
        return loginDataSource.verifyCurrentUser()
    }

    override suspend fun getLogin(username: String, password: String): Resource<User>? {
        return loginDataSource.buscarLogin(username,password)
    }

    override suspend fun registerUser(username:String,password:String): Resource<User>? {
        return loginDataSource.registerUser(username, password)
    }


    override suspend fun forgotPassword(email: String): Resource<Boolean>? {
        return loginDataSource.forgotPassword(email)
    }
}