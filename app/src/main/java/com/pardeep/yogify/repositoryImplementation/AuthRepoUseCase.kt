package com.pardeep.yogify.repositoryImplementation

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pardeep.yogify.repositorys.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepoUseCase(
    var auth: FirebaseAuth
) : AuthRepository {

    // login with text field value
    override suspend fun loginWithEmailAndPassword(
        userEmail: String,
        userPassword: String
    ): Boolean {
        return try {
            auth.signInWithEmailAndPassword(userEmail, userPassword).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepoImpl", "loginWithEmailAndPassword: login Error Occurred :${e.message}")
            false
        }

    }

    override suspend fun loginWithGoogle(token: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential)
            true
        } catch (e: Exception) {
            Log.e("AuthRepoImpl", "loginWithGoogle: Error occured in google login :${e.message} ")
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e("AuthRepoImpl", "signUp: Unable to create user with email :$email and password :$password")
            false
        }
    }

}