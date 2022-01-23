package com.example.projectkiwi.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectkiwi.data.remote.User
import com.example.projectkiwi.repository.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.projectkiwi.utils.Result

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel@Inject constructor(
    val loginRepo: LoginRepo
):ViewModel(){

    private val _registerState= MutableSharedFlow<Result<String>>()
    val registerState:SharedFlow<Result<String>> = _registerState

    fun createUser(
        username:String,
        email:String,
        password:String
    )=viewModelScope.launch {
        _registerState.emit(Result.Loading())
        if (username.isEmpty() || email.isEmpty()|| password.isEmpty()){
            _registerState.emit(Result.Error("Some fields are empty"))
            return@launch
        }
        val newUser=User(
            username,
            email,
            password
        )
        _registerState.emit(loginRepo.createUser(newUser))
    }


}