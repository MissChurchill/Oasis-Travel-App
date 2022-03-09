package com.example.beta.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.beta.FirebaseUserLiveData

class LoginViewModel: ViewModel() {
    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }


}