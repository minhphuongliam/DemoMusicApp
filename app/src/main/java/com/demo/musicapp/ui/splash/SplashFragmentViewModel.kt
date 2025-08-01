package com.demo.musicapp.ui.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Sealed class để định nghĩa các sự kiện điều hướng từ Splash.
// Giúp code ở Fragment rõ ràng hơn.
sealed class SplashNavigationEvent {
    object NavigateToStartFragment : SplashNavigationEvent()
}
@HiltViewModel
class SplashFragmentViewModel @Inject constructor() : ViewModel() {


}