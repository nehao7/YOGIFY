package com.pardeep.yogify.domain.repositorys

interface AuthRepository {

    suspend fun loginWithEmailAndPassword(userEmail :String, userPassword : String) : Boolean
    suspend fun loginWithGoogle(token :String) : Boolean
    suspend fun signUp(email: String, password: String): Boolean
}