package com.demo.musicapp.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.musicapp.data.repository.authentication.AuthRepository
import com.demo.musicapp.utils.InputValidator
import com.demo.musicapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Nhận state signup, check valid input
@HiltViewModel
class SignupFragmentViewModel @Inject constructor(
    private val authRepository: AuthRepository) : ViewModel() {

    private val TAG = "SignupViewModel"
    private var _signUpResponse : MutableLiveData<Response<Boolean>> = MutableLiveData()
    val signupResponse : LiveData<Response<Boolean>> = _signUpResponse

    // LiveData riêng cho các lỗi validation, chứa id -> string lỗi để hiển thị lỗi ngay dưới từng ô input
    private val _nameError = MutableLiveData<Int?>()
    val nameError: LiveData<Int?> = _nameError

    private val _emailError = MutableLiveData<Int?>()
    val emailError: LiveData<Int?> = _emailError

    private val _passwordError = MutableLiveData<Int?>()
    val passwordError: LiveData<Int?> = _passwordError

    private val _ageError = MutableLiveData<Int?>()
    val ageError : LiveData<Int?> = _ageError

    fun signUp(email : String, password: String, name: String, age: String ){
        Log.d(TAG, "signUp: $email $password $name $age")
        // Check input => OK
        if (!validateInputs(email, password, name, age)){
            return
        }
            // convert -> int sau validate age
            val ageInt = age.toInt()
        // cập nhật value
        viewModelScope.launch {
            _signUpResponse.value= Response.Loading
            _signUpResponse.value = authRepository.firebaseSignUp(email, password, name, ageInt)

        }
    }

    fun validateInputs(name: String, email: String, password: String, age: String): Boolean {
        // Sử dụng Validator để kiểm tra
        val nameResult = InputValidator.validateName(name)
        val emailResult = InputValidator.validateEmail(email)
        val passwordResult = InputValidator.validatePassword(password)
        val ageResult = InputValidator.validateAge(age)

        // Cập nhật LiveData với mã lỗi ==> trên frag tự dịch ra,  tránh mem leak nếu truyền String hẳn
        // String cần context , ID thì không, viewmodel sống lâu hơn frag
        _nameError.value = nameResult.errorMessageId
        _emailError.value = emailResult.errorMessageId
        _passwordError.value = passwordResult.errorMessageId
        _ageError.value = ageResult.errorMessageId

        // Trả về true nếu tất cả đều hợp lệ
        return nameResult.isSuccess && emailResult.isSuccess && passwordResult.isSuccess && ageResult.isSuccess
    }
}