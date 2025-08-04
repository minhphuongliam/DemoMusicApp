package com.demo.musicapp.data.repository.authentication

import android.util.Log
import com.demo.musicapp.utils.Response
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
// Tiêm phụ thuộc Firebase, đặt là instance duy nhất
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseFirestore
): AuthRepository{
    private val TAG = "AuthRepositoryImpl"

    override suspend fun firebaseSignUp(
        email: String,
        password: String,
        name: String,
        age: Int
    ): Response<Boolean> {
        Log.d(TAG, "firebaseSignUp: $email $password $name $age")
        return try {
            // chờ tạo xong rồi thêm vào db thông tin đăng kí
            auth.createUserWithEmailAndPassword(email,password).await()
            Log.d(TAG, "firebaseSignUp: Register Success")
            val uid = auth.currentUser?.uid ?: return Response.Failure(Exception("User not found"))
            val userProfile = hashMapOf(
                "uid" to uid,
                "email" to email,
                "display_name" to name,
                "age" to age,
                "createdAt" to Timestamp.now() // Fix timedate
            )
            database.collection("users")
                .document(uid)
                .set(userProfile).await()
            Log.d(TAG, "firebaseSignUp: Done")
            Response.Success(true)
        } catch (e : Exception) {
            Log.e(TAG, "firebaseSignUp: Failed!")
            Response.Failure(e)
        }
    }

    override suspend fun firebaseLogin(
        email: String,
        password: String
    ): Response<Boolean> {
        return try{
            auth.signInWithEmailAndPassword(email,password).await()
            Log.d(TAG, "firebaseLogin: Success")
            Response.Success(true)
        }catch (e: Exception){
            Log.e(TAG, "firebaseLogin: Failed!")
            Response.Failure(e)
        }
    }

    override suspend fun firebaseLogout(): Response<Boolean> {
        return try {
            auth.signOut()
            Response.Success(true)
        } catch(e: Exception){
            Response.Failure(e)
        }
    }

    override suspend fun firebaseIsLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}