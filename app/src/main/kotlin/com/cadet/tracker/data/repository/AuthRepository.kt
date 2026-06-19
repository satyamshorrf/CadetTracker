package com.cadet.tracker.data.repository

import com.cadet.tracker.data.local.UserDao
import com.cadet.tracker.data.models.User
import com.cadet.tracker.data.remote.ApiService
import com.cadet.tracker.data.remote.RegisterRequest
import com.cadet.tracker.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: ApiService,
    private val userDao: UserDao
) {

    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = apiService.getUser(authResult.user?.uid ?: "")
            userDao.insertUser(user)
            Result.Success(user)
        } catch (e: Exception) {
            Timber.e("Login error: ${e.message}")
            Result.Error(e.message ?: "Login failed")
        }
    }

    suspend fun register(name: String, email: String, phone: String, password: String, role: String, unit: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = User(
                uid = authResult.user?.uid ?: "",
                name = name,
                email = email,
                phone = phone,
                role = role,
                unit = unit,
                createdAt = System.currentTimeMillis()
            )
            val response = apiService.register(
                RegisterRequest(name, email, phone, password, role, unit)
            )
            if (response.success) {
                userDao.insertUser(user)
                Result.Success(user)
            } else {
                Result.Error(response.message)
            }
        } catch (e: Exception) {
            Timber.e("Registration error: ${e.message}")
            Result.Error(e.message ?: "Registration failed")
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            userDao.deleteAllUsers()
            Result.Success(Unit)
        } catch (e: Exception) {
            Timber.e("Logout error: ${e.message}")
            Result.Error(e.message ?: "Logout failed")
        }
    }

    fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.let { user ->
            User(uid = user.uid, email = user.email ?: "")
        }
    }
}
