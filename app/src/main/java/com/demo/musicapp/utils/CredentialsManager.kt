package com.demo.musicapp.utils

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

// Lo việc check credientials lưu trong offline
//class được inject bằng EncryptedSharedPref,
// Hilt tự tìm đến PreferencesModule -> công thức tạo enc kiểu  SharedPref => tiêm vào
@Singleton
class CredentialsManager @Inject constructor(
    private val prefs: SharedPreferences
) {
    //const dùng chung
    companion object{
        //fields
        private const val KEY_EMAIL = "saved_email"
        private const val KEY_PASSWORD = "saved_password"
    }

    fun saveCredentials(email: String, password: String ){
        prefs.edit()
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }
    fun getEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun getPassword(): String? {
        return prefs.getString(KEY_PASSWORD, null)
    }

    fun clearCredentials() {
        prefs.edit()
            .remove(KEY_EMAIL)
            .remove(KEY_PASSWORD)
            .apply()
    }

}