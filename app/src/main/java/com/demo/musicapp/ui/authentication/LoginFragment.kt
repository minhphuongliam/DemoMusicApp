package com.demo.musicapp.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demo.musicapp.R
import com.demo.musicapp.databinding.FragmentLoginBinding
import com.demo.musicapp.navigation.AppNavigator
import com.demo.musicapp.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment: Fragment() {
    @Inject
    lateinit var appNavigator: AppNavigator

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // gán listener, obsser
        setupListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners(){
        // Xử lý sự kiện bấm nút Login
        binding.buttonLoginLogin.setOnClickListener {
            val email = binding.editTextLoginEmail.text.toString().trim()
            val password = binding.editTextLoginPassword.text.toString()
            val rememberMe = binding.checkboxLoginRememberMe.isChecked

            // Gửi tất cả thông tin cho ViewModel xử lý
            viewModel.logIn(email, password, rememberMe)
        }

    }

    private fun observeViewModel() {
        //SỬA ĐỔI : LÀM VIỆC THEO LoginUiState
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            // Cập nhật trạng thái loading cho ProgressBar và nút bấm
            binding.ProgressBarLoginLoading.isVisible = state is LoginUiState.Loading
            binding.buttonLoginLogin.isEnabled = state !is LoginUiState.Loading

            // Xử lý các trạng thái UI
            when (state) {
                is LoginUiState.Success -> {
                    // Đăng nhập thành công (cả online và offline)
                    // nav về home
                    appNavigator.navigateToHome()
                }
                is LoginUiState.Error -> {
                    // Lấy chuỗi lỗi từ ID mà ViewModel cung cấp
                    val errorMessage = getString(state.messageId)
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
                // Các trạng thái khác không cần hành động gì đặc biệt ở đây
                is LoginUiState.Idle, is LoginUiState.Loading -> { /* Đã xử lý ở trên */ }
            }
        }

        // quan sát để tự động điền thông tin đã lưu (nếu có)
        viewModel.savedEmail.observe(viewLifecycleOwner) { email ->
            binding.editTextLoginEmail.setText(email)
            // Nếu có email, tự động bật checkbox "Remember me"
            binding.checkboxLoginRememberMe.isChecked = (email != null)
        }
        viewModel.savedPassword.observe(viewLifecycleOwner) { password ->
            binding.editTextLoginPassword.setText(password)
        }

        // quan sát lỗi validation của từng trường
        viewModel.emailError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextLoginEmail.error = errorId?.let { getString(it) }
        }
        viewModel.passwordError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextLoginPassword.error = errorId?.let { getString(it) }
        }
    }

}