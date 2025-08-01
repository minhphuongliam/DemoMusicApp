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
// --- THAY ĐỔI 2: Sửa lại hoàn toàn định nghĩa của Module ---
// - Phải là `abstract class`.
// - KHÔNG implement AppNavigator.
// - KHÔNG có @Inject constructor(). Vai trò của nó chỉ là "dạy" Hilt.
abstract class NavigationModule {

    /**
     * Dùng @Binds để bind interface `AppNavigator` với implementation `AppNavigatorImpl`.
     * Đây là cách làm hiệu quả nhất của Hilt cho việc này.
     * Nó nói với Hilt: "Khi có ai đó yêu cầu (@Inject) một AppNavigator,
     * hãy đi tạo một AppNavigatorImpl (Hilt biết cách tạo vì AppNavigatorImpl có @Inject constructor)
     * và cung cấp nó."
     */
    @Binds
    @Singleton
    abstract fun bindAppNavigator(
        // Tham số đầu vào: Implementation mà Hilt cần tạo
        appNavigatorImpl: AppNavigatorImpl
        // Giá trị trả về: Interface mà Hilt sẽ cung cấp cho người yêu cầu
    ): AppNavigator
}