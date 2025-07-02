package com.pardeep.yogify.local.repoImpl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pardeep.yogify.domain.repositorys.AuthRepository
import com.pardeep.yogify.local.preference.UsesPreference
import kotlinx.coroutines.tasks.await

class AuthRepoImpl(
    private var auth: FirebaseAuth,
    private val usesPreference: UsesPreference
) : AuthRepository {

    // login with text field value
    override suspend fun loginWithEmailAndPassword(
        userEmail: String,
        userPassword: String
    ): Boolean {
        return try {
            auth.signInWithEmailAndPassword(userEmail, userPassword).await()
            usesPreference.userLoginStatus(true)
            true
        } catch (e: Exception) {
            Log.e("AuthRepoImpl", "loginWithEmailAndPassword: login Error Occurred :${e.message}")
            usesPreference.userLoginStatus(false)
            false
        }

    }

    override suspend fun loginWithGoogle(token: String): Boolean {
        return try {
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential)
            usesPreference.userLoginStatus(true)
            true
        } catch (e: Exception) {
            Log.e("AuthRepoImpl", "loginWithGoogle: Error occured in google login :${e.message} ")
            usesPreference.userLoginStatus(false)
            false
        }
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            Log.e(
                "AuthRepoImpl",
                "signUp: Unable to create user with email :$email and password :$password"
            )
            false
        }
    }

}