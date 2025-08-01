package com.demo.musicapp.navigation

import android.content.Intent
import androidx.navigation.NavController
import com.demo.musicapp.R
import java.lang.ref.WeakReference
import java.lang.reflect.Constructor
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
        navController?.get()?.navigate(R.id.action_splashFragment_to_startFragment)
    }

    override fun openStartToLogin() {
        navController?.get()?.navigate(R.id.action_startFragment_to_loginFragment)
    }

    override fun openStartToSignup() {
        navController?.get()?.navigate(R.id.action_startFragment_to_signupFragment)
    }

    override fun openSignupToLogin() {
        navController?.get()?.navigate(R.id.action_signupFragment_to_loginFragment)
    }

    override fun navigateToHome() {
        navController?.get()?.context?.let { context ->
            val intent = Intent("com.demo.musicapp.ACTION_OPEN_HOME")
            // - FLAG_ACTIVITY_NEW_TASK: Cần thiết khi gọi startActivity từ một context không phải là Activity.
            // - FLAG_ACTIVITY_CLEAR_TASK: Xóa tất cả các Activity trong task hiện tại (AuthActivity sẽ bị xóa).
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun navigateUp() {
        navController?.get()?.navigateUp()
    }
}