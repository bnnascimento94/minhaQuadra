package com.example.minhaquadra.data.repository.datasource

import com.example.minhaquadra.data.model.Login

interface LoginDataSource {

    suspend fun buscarLogin(username:String, password:String): Login?

    suspend fun forgotPassword(username:String, password:String): Login?

    suspend fun registerUser(login: Login): Login?
}