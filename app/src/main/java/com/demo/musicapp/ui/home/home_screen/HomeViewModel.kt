package com.demo.musicapp.ui.home.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.musicapp.utils.CredentialsManager
import com.demo.musicapp.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class HomeNavigationEvent {
    object NavigateToAuth : HomeNavigationEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val credentialsManager: CredentialsManager
) : ViewModel() {

    private val _navigationEvent = MutableLiveData<HomeNavigationEvent>()
    val navigationEvent: LiveData<HomeNavigationEvent> = _navigationEvent

    fun onLogoutClicked() {
        viewModelScope.launch {
            // Gọi hàm logout trong SessionManager
            // xóa cả Firebase session và SharedPreferences
            sessionManager.logout()
            credentialsManager.clearCredentials()
            // Phát sự kiện để báo cho View biết cần chuyển về luồng Auth
            _navigationEvent.value = HomeNavigationEvent.NavigateToAuth
        }
    }
}