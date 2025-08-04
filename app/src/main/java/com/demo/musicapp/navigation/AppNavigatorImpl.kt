package com.demo.musicapp.navigation

import android.content.Intent
import android.util.Log
import androidx.navigation.NavController
import com.demo.musicapp.R
import com.demo.musicapp.ui.authentication.AuthActivity
import com.demo.musicapp.ui.home.HomeActivity
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // Đảm bảo chỉ có MỘT instance duy nhất cho toàn ứng dụng
class AppNavigatorImpl @Inject constructor() : AppNavigator {
    // `navController` bây giờ không còn là một `NavController` trực tiếp nữa,
    // mà là một `WeakReference` (Tham chiếu yếu) trỏ đến `NavController`.
    // giữ 1 tham chiếu yếu, ==> GC tự xóa NavController khi ko dùng.  tránh mem leak
    // nếu dùng tham chiếu mạnh private var navController: NavController thì AppNavigatorImpl sẽ "níu kéo", không cho phép NavController bị hủy
    // kể cả khi activity dùng nó bị hủy
    private var navController: WeakReference<NavController>? = null
    /**
    gắn NavController của Activity đang hoạt động vào Navigator.
    gọi từ `onCreate()` của mỗi Activity
     */
    override fun bind(navController: NavController) {
        // bọc NavController nhận được trong một WeakReference trước khi lưu trữ.
        this.navController = WeakReference(navController)
    }
    /** gán null ngắt liên kết*/
    override fun unbind() {
        this.navController = null
    }

    //note: navController?.get()?
    // navController? có thể là null khi unbind,
    // get()?: lấy bên trong là 1 NavController , có thể bị GC dọn do là 1 WeakReference

    override fun openSplashToStart() {
        val nc = navController?.get()
        Log.d("AppNavigator", "openSplashToStart() called. navController=$nc")

        if (nc != null) {
            nc.navigate(R.id.action_splashFragment_to_startFragment)
        } else {
            Log.e("AppNavigator", "NavController is null! Did you forget to bind()?")
        }
    }

    override fun openSplashToLogin() {
        navController?.get()?.navigate(R.id.action_splashFragment_to_loginFragment)
    }

    override fun openStartToLogin() {
        navController?.get()?.navigate(R.id.action_startFragment_to_loginFragment)
    }

    override fun openStartToSignup() {
        navController?.get()?.navigate(R.id.action_startFragment_to_signupFragment)
    }

    override fun openSignupToLogin() {
        navController?.get()?.navigate(R.id.loginFragment)
    }

    override fun navigateToHome() {
        navController?.get()?.context?.let { context ->
            // THAY ĐỔI: Dùng Intent tường minh tới HomeActivity.class.java cho đơn giản và an toàn hơn
            val intent = Intent(context, HomeActivity::class.java)
            // - FLAG_ACTIVITY_NEW_TASK: Cần thiết khi gọi startActivity từ một context không phải là Activity.
            // - FLAG_ACTIVITY_CLEAR_TASK: Xóa tất cả các Activity trong task hiện tại (AuthActivity sẽ bị xóa).
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }
    override fun navigateToStartAndClearBackStack() {
        // Hàm này sẽ hữu ích khi logout từ Home
        // Nó sẽ đi đến Start và xóa tất cả những gì phía trên nó
        navController?.get()?.navigate(
            R.id.startFragment,
            null,
            androidx.navigation.NavOptions.Builder()
                // THAY ĐỔI: Dùng graph.startDestinationId thay vì graph.id để an toàn hơn
                .setPopUpTo(navController?.get()?.graph?.startDestinationId ?: 0, true)
                .build()
        )
    }
    override fun navigateToAuthAndClearStack() {
        navController?.get()?.context?.let { context ->
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun navigateUp() {
        navController?.get()?.navigateUp()
    }
}
