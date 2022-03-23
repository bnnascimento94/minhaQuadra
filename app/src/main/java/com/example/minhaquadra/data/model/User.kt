package com.example.minhaquadra.data.model

import kotlin.String


data class User (
     val uid: String?,
     val displayName: String?,
     val email: String?,
     val photoUrl: String?,
     val isEmailVerified:Boolean?
){
     fun userToHash(): Map<String, Any?>{
          return mapOf(
               "uid" to uid,
               "displayName" to displayName,
               "email" to email,
               "photoUrl" to photoUrl,
               "isEmailVerified" to isEmailVerified)
     }

}