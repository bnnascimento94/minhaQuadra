package com.example.minhaquadra.data.repository.datasourceimpl

import com.example.minhaquadra.data.model.Login
import com.example.minhaquadra.data.repository.datasource.LoginDataSource

class LoginDataSourceImpl : LoginDataSource {

    override suspend fun buscarLogin(username: String, password: String): Login? {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(username: String, password: String): Login? {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(login: Login): Login? {
        TODO("Not yet implemented")
    }
}