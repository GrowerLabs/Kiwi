package com.example.projectkiwi.repository

import com.example.projectkiwi.data.remote.LoginApi
import com.example.projectkiwi.data.remote.User
import com.example.projectkiwi.utils.Result
import com.example.projectkiwi.utils.SessionManager
import com.example.projectkiwi.utils.isNetworkConnected
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    val logInApi:LoginApi,
    val sessionManager: SessionManager
) :LoginRepo{

    override suspend fun createUser(user: User): Result<String> {
        return try {
            if (!isNetworkConnected(sessionManager.context)){
                Result.Error<String>("No internet connection")
            }
            val result=logInApi.signUp(user)
            if (result.isSuccessful){
                Result.Success("user created successfully")
            }else{
                Result.Error(result.errorBody().toString())
            }
        }catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message?:"Some Problem Occurred")
        }
    }

    override suspend fun login(username: String, password: String): Result<String> {

            return try {
                if (!isNetworkConnected(sessionManager.context)){
                    Result.Error<String>("No internet connection")
                }
                val result=logInApi.login(username,password)
                if (result.isSuccessful){
                    sessionManager.updateSession(result.body()?.token!!,username)
                    Result.Success("logged in successfully")
                }else{
                    Result.Error(result.errorBody().toString())
                }
            }catch (e:Exception){
                e.printStackTrace()
                Result.Error(e.message?:"Some Problem Occurred")
            }
        }

    override suspend fun getUser(): Result<User> {
        return try {
            val username=sessionManager.getNameToken()
            if(username==null){
                Result.Error<User>("user not logged in")
            }
            Result.Success(User(username!!,"",""))
        }catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message?:"some problem occurred")
        }
    }

    override suspend fun logOut(): Result<String> {

        return  try {
            sessionManager.logout()
            Result.Success("Logged out successfully")
        }catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message?:"some problem occured")
        }
    }
}
