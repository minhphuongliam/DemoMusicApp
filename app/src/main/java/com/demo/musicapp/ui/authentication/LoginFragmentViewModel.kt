package com.demo.musicapp.ui.authentication

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.musicapp.data.repository.authentication.AuthRepository
import com.demo.musicapp.utils.CredentialsManager
import com.demo.musicapp.utils.InputValidator
import com.demo.musicapp.utils.Response
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.demo.musicapp.R
import javax.inject.Inject

// Lớp trạng thái UI để giao tiếp với Fragment một cách rõ ràng
sealed class LoginUiState {
    object Idle : LoginUiState() // Trạng thái ban đầu
    object Loading : LoginUiState() // Đang xử lý
    object Success : LoginUiState() // Đăng nhập thành công (cả online và offline)
    data class Error(@StringRes val messageId: Int) : LoginUiState() // Có lỗi, trả về ID của string
}

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val credentialsManager: CredentialsManager
    // Bỏ SessionManager vì nó chỉ cần thiết ở SplashViewModel
) : ViewModel(){

    private val TAG = "LoginViewModel"

    // THAY ĐỔI 1: Sử dụng LoginUiState thay vì Response<Boolean>
    private val _loginState = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val loginState: LiveData<LoginUiState> = _loginState

    // LiveData để tự động điền thông tin khi khởi động
    private val _savedEmail = MutableLiveData<String?>()
    val savedEmail: LiveData<String?> = _savedEmail

    private val _savedPassword = MutableLiveData<String?>()
    val savedPassword: LiveData<String?> = _savedPassword

    //Live data chứa id string lỗi
    private val _emailError = MutableLiveData<Int?>()
    val emailError : LiveData<Int?> = _emailError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError : LiveData<Int?> = _passwordError

    init {
        // Ngay khi ViewModel được tạo thử tải thông tin đã lưu
        loadSavedCredentials()
    }

    // Load email với pass lên
    private fun loadSavedCredentials() {
        _savedEmail.value = credentialsManager.getEmail()
        _savedPassword.value = credentialsManager.getPassword()
    }

    // đăng nhập với chêck credient, check xem có remember ko
    fun logIn(email: String, password: String, rememberMe: Boolean) {
        if (!validateInputs(email, password)) {
            return
        }

        viewModelScope.launch {
            // Cập nhật trạng thái Loading
            _loginState.value = LoginUiState.Loading

            // Gọi repository để đăng nhập
            val result = authRepository.firebaseLogin(email, password)

            // Xử lý kết quả trả về
            when (result) {
                is Response.Success -> {
                    // Đăng nhập online thành công
                    if (rememberMe) {
                        credentialsManager.saveCredentials(email, password)
                    } else {
                        credentialsManager.clearCredentials()
                    }
                    _loginState.value = LoginUiState.Success
                }

                is Response.Failure -> {
                    // Nếu thất bại, xử lý các loại exception khác nhau
                    handleLoginFailure(result.e, email, password, rememberMe)
                }
                is Response.Loading -> {
                    // Đã xử lý  state loading
                }
            }
        }
    }
    /**
     * Hàm riêng để xử lý các kịch bản lỗi Firebase
     */
    private fun handleLoginFailure(e: Exception, emailAttempt: String, passwordAttempt: String, rememberMe: Boolean) {
        when (e) {
            is FirebaseNetworkException -> {
                // Lỗi mạng, kiểm tra "Remember Me"
                if (rememberMe) {
                    val savedEmail = credentialsManager.getEmail()
                    val savedPassword = credentialsManager.getPassword()
                    if (emailAttempt == savedEmail && passwordAttempt == savedPassword) {
                        // Cho phép đăng nhập offline nếu thông tin khớp
                        Log.d(TAG, "Offline login successful with saved credentials.")
                        _loginState.value = LoginUiState.Success
                    } else {
                        // Không khớp hoặc chưa từng lưu, báo lỗi mạng
                        _loginState.value = LoginUiState.Error(R.string.error_no_internet_connection)
                    }
                } else {
                    // Không tick "Remember Me", chỉ đơn giản là báo lỗi mạng
                    _loginState.value = LoginUiState.Error(R.string.app_go_offline)
                }
            }
            is FirebaseAuthInvalidUserException -> {
                // Email không tồn tại
                _loginState.value = LoginUiState.Error(R.string.error_email_is_not_registered)
            }
            is FirebaseAuthInvalidCredentialsException -> {
                // Sai mật khẩu
                _loginState.value = LoginUiState.Error(R.string.error_password_is_incorrect)
            }
            else -> {
                // Các lỗi không xác định khác
                Log.e(TAG, "Login failed with unknown exception: ", e)
                _loginState.value = LoginUiState.Error(R.string.error_login_failed)
            }
        }
    }
    fun validateInputs(email: String, password: String): Boolean {
        // Sử dụng Validator để kiểm tra
        val emailResult = InputValidator.validateEmail(email)
        val passwordResult = InputValidator.validatePassword(password)

        _emailError.value = emailResult.errorMessageId
        _passwordError.value = passwordResult.errorMessageId

        // Trả về true nếu tất cả đều hợp lệ
        return emailResult.isSuccess && passwordResult.isSuccess
    }
}