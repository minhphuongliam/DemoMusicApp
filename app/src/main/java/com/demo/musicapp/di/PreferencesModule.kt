package com.demo.musicapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module chuyên cung cấp các dependency liên quan đến
 * việc lưu trữ dữ liệu cục bộ như SharedPreferences.
 */
// vòng đời toàn app
@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    // công thức tạo EncSharedPref duy nhất
    // cần truyền context để biết đường dẫn -> app trong thư mục ứng dụng để lưu file secret_user_prefs.xml
    // và để tương tác với Androdi Key Store -> mã hóa
    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context : Context): SharedPreferences{
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC) //masterkey để decrp & enc

        return EncryptedSharedPreferences.create(
                "secret_user_prefs", // Tên file prefs
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, //algor mã hóa key
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM //algor mã hóa value
            )
    }
}