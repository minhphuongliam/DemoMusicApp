package com.demo.musicapp.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.demo.musicapp.R
/**
 * Data class để đóng gói kết quả của một lần kiểm tra đầu vào.
 * @param isSuccess True nếu hợp lệ, false nếu không.
 * @param errorMessageId ID của chuỗi lỗi từ R.string nếu không hợp lệ.
 */
data class ValidationResult(
    val isSuccess: Boolean,
    @StringRes val errorMessageId: Int? = null
)

/**
 * Singleton object chứa các hàm tiện ích để kiểm tra đầu vào của người dùng.
 * Các hàm sẽ trả về một đối tượng [ValidationResult].
 */
object InputValidator {

    /** Kiểm tra tên không được rỗng và có độ dài hợp lý. */
    fun validateName(name: String): ValidationResult {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) {
            return ValidationResult(false, R.string.error_field_required)
        }
        if (trimmedName.length !in 2..50) {
            return ValidationResult(false, R.string.error_name_invalid_length)
        }
        return ValidationResult(true)
    }

    /** Kiểm tra email hợp lệ. */
    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, R.string.error_field_required)
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, R.string.error_email_invalid)
        }
        return ValidationResult(true)
    }

    /** Kiểm tra mật khẩu có độ dài tối thiểu. */
    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(false, R.string.error_field_required)
        }
        if (password.length < 8) {
            return ValidationResult(false, R.string.error_password_too_short)
        }
        return ValidationResult(true)
    }

    /** Kiểm tra tuổi là một số và nằm trong khoảng hợp lý (13-120). */
    fun validateAge(ageStr: String): ValidationResult {
        if (ageStr.isBlank()) {
            return ValidationResult(false, R.string.error_field_required)
        }
        val ageInt = ageStr.toIntOrNull()
        if (ageInt == null || ageInt !in 13..120) {
            return ValidationResult(false, R.string.error_age_invalid)
        }
        return ValidationResult(true)
    }
}
