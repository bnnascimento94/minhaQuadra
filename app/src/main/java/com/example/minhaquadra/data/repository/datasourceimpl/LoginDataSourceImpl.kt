package com.example.minhaquadra.data.repository.datasourceimpl

import com.example.minhaquadra.data.model.User
import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.util.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginDataSourceImpl(private val firebaseAuth: FirebaseAuth) : LoginDataSource {

    override suspend fun buscarLogin(username: String, password: String): Resource<User> {
        return try{
            val data = firebaseAuth.signInWithEmailAndPassword(username,password).await()
            return Resource.Success(User(data.user?.uid,
                data.user?.displayName,
                data.user?.email,
                data.user?.photoUrl.toString(),
                data.user?.isEmailVerified))
        }catch (e : Exception){
            return Resource.Error(e.message)
        }
    }

    override suspend fun forgotPassword(username: String): Resource<Boolean> {
        return try{
            val data = firebaseAuth.sendPasswordResetEmail(username).isSuccessful
            return Resource.Success(data)
        }catch (e : Exception){
            return Resource.Error(e.message)
        }
    }

    override suspend fun registerUser(username: String?, password: String?): Resource<User>? {
        return try{
            val data = firebaseAuth.createUserWithEmailAndPassword(username!!,password!!).await()
            return Resource.Success(User(data.user?.uid,
                data.user?.displayName,
                data.user?.email,
                data.user?.photoUrl.toString(),
                data.user?.isEmailVerified))
        }catch (e : Exception){
            return Resource.Error(e.message)
        }
    }
}