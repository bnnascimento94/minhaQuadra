package com.example.minhaquadra.domain.repository

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.util.Resource

interface LoginRepository {

    suspend fun getUserConnected(): Resource<User>?

    suspend fun getLogin(username:String,password:String): Resource<User>?

    suspend fun registerUser(username:String,password:String): Resource<User>?

    suspend fun forgotPassword(email:String): Resource<Boolean>?

}