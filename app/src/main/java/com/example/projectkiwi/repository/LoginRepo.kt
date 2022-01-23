package com.example.projectkiwi.repository

import com.example.projectkiwi.data.remote.User
import com.example.projectkiwi.utils.Result
interface LoginRepo {
   suspend fun createUser(user: User):Result<String>
   suspend fun login(username:String,password:String):Result<String>
   suspend fun getUser():Result<User>
   suspend fun logOut():Result<String>
}