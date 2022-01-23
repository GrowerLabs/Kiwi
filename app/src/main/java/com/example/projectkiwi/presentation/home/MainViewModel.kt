package com.example.projectkiwi.presentation.home

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
class MainViewModel @Inject constructor(
    val loginRepo: LoginRepo

):ViewModel() {

    private val _currentUserState= MutableSharedFlow<Result<User>>()
    val currentUserState: SharedFlow<Result<User>> = _currentUserState


   fun getCurrentUser()=viewModelScope.launch {
       _currentUserState.emit(Result.Loading())
       _currentUserState.emit(loginRepo.getUser())
   }

    fun logout()=viewModelScope.launch {
        val result=loginRepo.logOut()
        if(result is Result.Success){
            getCurrentUser()
        }
    }



}