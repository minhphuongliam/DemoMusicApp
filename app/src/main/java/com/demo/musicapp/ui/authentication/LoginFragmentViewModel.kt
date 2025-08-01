package com.demo.musicapp.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.musicapp.data.repository.authentication.AuthRepository
import com.demo.musicapp.utils.CredentialsManager
import com.demo.musicapp.utils.InputValidator
import com.demo.musicapp.utils.Response
import com.demo.musicapp.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val credentialsManager: CredentialsManager
) : ViewModel(){

    private val TAG = "LoginViewModel"
    private var _logInResponse : MutableLiveData<Response<Boolean>> = MutableLiveData()
    val logInResponse : LiveData<Response<Boolean>> = _logInResponse

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
        // Ngay khi ViewModel được tạo, hãy thử tải thông tin đã lưu
        loadSavedCredentials()
    }

    // Load email với pass lên
    private fun loadSavedCredentials() {
        _savedEmail.value = credentialsManager.getEmail()
        _savedPassword.value = credentialsManager.getPassword()
    }

    // đăng nhập với chêck credient, check xem có remember ko
    fun logIn(email : String, password: String, rememberMe: Boolean)
    {
        Log.d(TAG, "logIn: $email $password")
        //check input
        if(!validateInputs(email, password)){
            return
        }
        viewModelScope.launch {
            _logInResponse.value = Response.Loading
            val result = authRepository.firebaseLogin(email, password)

            if (result is Response.Success && result.data) {

                // check box nhớ mật khẩu
                if (rememberMe) {
                    //tick thì lưu
                    credentialsManager.saveCredentials(email, password)
                } else {
                    // Nếu không, hãy xóa mọi thông tin cũ (nếu có)
                    credentialsManager.clearCredentials()
                }
            }

            _logInResponse.value = result
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