package com.demo.musicapp.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.demo.musicapp.databinding.FragmentSignupBinding
import com.demo.musicapp.utils.Response
import com.demo.musicapp.R
import com.demo.musicapp.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignupFragment  : Fragment(){
    //edit: chèn navigation
    @Inject
    lateinit var navigator: AppNavigator

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!

    // lấy viewmodel qua hilt, gắn với frag
    private val viewModel : SignupFragmentViewModel by viewModels()

    //overide các hàm
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup các listener & observe model
        setupListeners()
        observerViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners(){
        binding.buttonSignupSignup.setOnClickListener {
            val name = binding.editTextSignupFullname.text.toString()
            val email = binding.editTextSignupEmail.text.toString()
            val password = binding.editTextSignupPassword.text.toString()
            val age = binding.editTextSignupAge.text.toString()
            viewModel.signUp(email,password,name,age)
        }

        binding.buttonBack.setOnClickListener {
            navigator.navigateUp()
        }


    }

    private fun observerViewModel(){
        // quan sát trạng thái signup firebase,
        viewModel.signupResponse.observe(viewLifecycleOwner){ response ->
            // load ui tương ứng state,
            binding.ProgressBarSignupLoading.isVisible = response is Response.Loading
            binding.buttonSignupSignup.isEnabled = response !is Response.Loading // vô hiệu hóa lúc load

            when (response) {
                is Response.Success -> {
                    Toast.makeText(context, getString(R.string.signup_sucess), Toast.LENGTH_SHORT).show()

                }
                is Response.Failure -> {
                    val errorMessage = response.e.message ?: getString(R.string.signup_error)
                    Toast.makeText(context, "${resources.getString(R.string.error)}: $errorMessage", Toast.LENGTH_LONG).show()
                }
                is Response.Loading -> { /* Đã xử lý */ }
            }

        }

        // Quan sát lỗi validation
        viewModel.nameError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextSignupFullname.error = errorId?.let { getString(it) }
        }
        viewModel.emailError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextSignupEmail.error = errorId?.let { getString(it) }
        }
        viewModel.passwordError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextSignupPassword.error = errorId?.let { getString(it) }
        }
        viewModel.ageError.observe(viewLifecycleOwner) { errorId ->
            binding.editTextSignupAge.error = errorId?.let { getString(it) }
        }
    }
}