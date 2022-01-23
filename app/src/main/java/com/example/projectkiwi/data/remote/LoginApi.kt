package com.example.projectkiwi.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded

    @POST("api-token-auth/")
    suspend fun login(
        @Field("username")username:String,
        @Field("password")password:String
    ):Response<LoginResponse>

    @POST("sign-up/")
    suspend fun signUp(
        @Body user: User
    ):Response<User>

}