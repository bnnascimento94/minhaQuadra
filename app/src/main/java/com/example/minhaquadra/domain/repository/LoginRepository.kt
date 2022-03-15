package com.example.minhaquadra.domain.repository

import com.example.minhaquadra.data.model.Login

interface LoginRepository {

    suspend fun getLogin(username:String,password:String): Login?

    suspend fun registerUser(login: Login): Login?

    suspend fun forgotPassword(email:String, password: String): Login?

}