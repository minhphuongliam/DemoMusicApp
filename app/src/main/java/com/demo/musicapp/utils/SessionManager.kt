package com.demo.musicapp.utils

import com.demo.musicapp.data.repository.authentication.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

//Quản lý trạng thái phiên đăng nhập, tách bạch với Repo ,đơn giản thì check firebase,
@Singleton
class SessionManager @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Kiểm tra xem người dùng có đang trong một phiên đăng nhập hợp lệ hay không.
     * Hàm này chỉ đơn giản là ủy quyền cho AuthRepository.
     */
    suspend fun isLoggedIn(): Boolean {
        return authRepository.firebaseIsLoggedIn()
    }

    /**
     * Thực hiện đăng xuất. Cũng ủy quyền cho AuthRepository.
     */
    suspend fun logout() {
        authRepository.firebaseLogout()
    }
}