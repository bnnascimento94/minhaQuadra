package com.example.minhaquadra.data.model

import kotlin.String


data class User (
     val uid: String? = null,
     val displayName: String? = null,
     val email: String? = null,
     val photoUrl: String? = null,
     val isEmailVerified:Boolean? = null
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