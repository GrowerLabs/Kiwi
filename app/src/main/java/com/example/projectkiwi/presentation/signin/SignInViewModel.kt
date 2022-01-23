package com.example.projectkiwi.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectkiwi.data.remote.User
import com.example.projectkiwi.repository.LoginRepo
import com.example.projectkiwi.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    val loginRepo: LoginRepo
):ViewModel(){


    private val _loginState= MutableSharedFlow<Result<String>>()
    val loginState: SharedFlow<Result<String>> = _loginState

    fun loginUser(
        username:String,

        password:String
    )=viewModelScope.launch {
        _loginState.emit(Result.Loading())
        if (username.isEmpty() ||  password.isEmpty()){
            _loginState.emit(Result.Error("Some fields are empty"))
            return@launch
        }
        _loginState.emit(loginRepo.login(username, password))
    }
}