package com.demo.musicapp.di

import androidx.navigation.NavController
import com.demo.musicapp.navigation.AppNavigator
import java.lang.ref.WeakReference
import javax.inject.Inject
import com.demo.musicapp.R
import com.demo.musicapp.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Cài đặt vào SingletonComponent vì AppNavigator là Singleton.
@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule @Inject constructor(): AppNavigator {

    /**
     * Dùng @Binds để bind interface `AppNavigator` với implementation `AppNavigatorImpl`.
     * Đây là cách làm hiệu quả nhất của Hilt cho việc này.
     * Nó nói với Hilt: "Khi có ai đó yêu cầu (@Inject) một AppNavigator,
     * hãy đi tạo một AppNavigatorImpl (Hilt biết cách tạo vì có @Inject constructor)
     * và cung cấp nó."
     */
    @Binds
    @Singleton
    abstract fun bindAppNavigator(
        appNavigatorImpl: AppNavigatorImpl
    ): AppNavigator


}