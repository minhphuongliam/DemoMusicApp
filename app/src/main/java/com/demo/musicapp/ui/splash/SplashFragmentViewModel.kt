package com.demo.musicapp.ui.splash

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.musicapp.data.repository.authentication.AuthRepository
import com.demo.musicapp.utils.CredentialsManager
import com.demo.musicapp.utils.Response
import com.demo.musicapp.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.demo.musicapp.R
import javax.inject.Inject

// Sealed class để định nghĩa các sự kiện điều hướng từ Splash.
// Giúp code ở Fragment rõ ràng hơn.
sealed class SplashNavigationEvent {
    object NavigateToHome : SplashNavigationEvent()
    object NavigateToStart : SplashNavigationEvent()
    // Sự kiện mới để đi đến Login và mang theo thông báo lỗi
    data class NavigateToLoginWithError(@StringRes val messageId: Int) : SplashNavigationEvent()
}
@HiltViewModel
class SplashFragmentViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val credentialsManager: CredentialsManager,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val TAG ="SplashViewModel"
    //LiveData để có thể observe trên frag điều hướng decideNextScreen
    //Truyền nguyên kiểu event object/ class navigate
    private val _navigationEvent = MutableLiveData<SplashNavigationEvent>()
    val navigationEvent : LiveData<SplashNavigationEvent> = _navigationEvent

    fun deviceNextScreen(){
        viewModelScope.launch {
            // Delay splash để hiển thị logo
            delay(1500)

            // Ưu tiên 1 : Kiểm tra đăng nhập hiện tại của firebase.
            if(sessionManager.isLoggedIn()) { //còn trong phiên firebase thì về Home
                Log.d(TAG, "Firebase session is active. Navigating to Home.")
                _navigationEvent.value = SplashNavigationEvent.NavigateToHome
                return@launch
            }

            // Ưu tiên 2, không có session thì thử đăng nhập lại bằng thông tin đã lưu
            val savedEmail = credentialsManager.getEmail()
            val savedPassword = credentialsManager.getPassword()

            if (savedEmail != null && savedPassword != null) {
                Log.d(TAG, "Found saved credentials. Attempting to re-login.")
                // Có thông tin đã lưu, thử đăng nhập lại
                val result = authRepository.firebaseLogin(savedEmail, savedPassword)
                if(result is Response.Success && result.data) // succes, true
                {
                    Log.d(TAG, "Re-login successful. Navigating to Home.")
                    _navigationEvent.value = SplashNavigationEvent.NavigateToHome
                } else{
                    // Đăng nhập lại thất bại (sai pass, mất mạng, user bị xóa...)
                    Log.d(TAG, "Re-login failed. Navigating to Login with error.")
                    // Xóa thông tin cũ đi vì nó không còn hợp lệ
                    credentialsManager.clearCredentials()
                    // Đi đến màn hình Login với thông báo lỗi
                    _navigationEvent.value = SplashNavigationEvent.NavigateToLoginWithError(R.string.error_login_failed)
                }
            }else
            {
                // ƯU TIÊN 3: Không có session, không có thông tin lưu -> người dùng mới
                Log.d(TAG, "No session or saved credentials. Navigating to Start.")
                _navigationEvent.value = SplashNavigationEvent.NavigateToStart
            }
        }
    }

}