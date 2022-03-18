package com.example.minhaquadra.data.repository.datasource

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource

interface LoginDataSource {

    suspend fun buscarLogin(username:String, password:String): Resource<User>?

    suspend fun forgotPassword(username:String): Resource<Boolean>?

    suspend fun registerUser(username: String?, password: String?): Resource<User>?
}