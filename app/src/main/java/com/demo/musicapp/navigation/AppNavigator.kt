package com.demo.musicapp.navigation

import androidx.navigation.NavController

/**
 Tạo Navigation? dùng chung nhiều activity.
 1.Bản thân Navigator là một Singleton: Chỉ có một object AppNavigator tồn tại trong suốt vòng đời ứng dụng.
 2.Navigator có thể "gắn" (bind) và "tháo" (unbind): Nó sẽ giữ một tham chiếu (WeakReference) đến NavController của Activity đang hoạt động.
 3. Các Activity chịu trách nhiệm bind/unbind
 */
interface AppNavigator {
    // Gắn NavController của Activity hiện tại vào Navigator
    fun bind (navController: NavController)
    // Tháo NavController khi Actiivty đó bị hủy
    fun unbind()

    // --- Các hành động trong luồng xác thực (Auth Flow) ---

    /** Chuyển từ màn hình Splash sang Start sau khi kiểm tra xong. */
    fun openSplashToStart()

    /** Chuyển từ màn hình Start sang Login. */
    fun openStartToLogin()

    /** Chuyển từ màn hình Start sang Signup. */
    fun openStartToSignup()

    /** Chuyển từ màn hình Signup sang Login. */
    fun openSignupToLogin()

    // --- Các hành động chuyển Activity ---

    /** Chuyển từ luồng xác thực sang màn hình chính (HomeActivity). */
    fun navigateToHome()

    // --- Hành động chung ---

    /** Quay lại màn hình trước đó trong back stack. */
    fun navigateUp()
}