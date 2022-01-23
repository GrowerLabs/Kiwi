package com.example.projectkiwi.di

import android.content.Context
import com.example.projectkiwi.data.remote.LoginApi
import com.example.projectkiwi.repository.LoginRepo
import com.example.projectkiwi.repository.LoginRepoImpl
import com.example.projectkiwi.utils.Constants.BASE_URL
import com.example.projectkiwi.utils.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()


    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = SessionManager(context)

    @Singleton
    @Provides
    fun provideLoginApi(): LoginApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepo(
        loginApi: LoginApi,
        sessionManager: SessionManager
    ):LoginRepo{
        return LoginRepoImpl(loginApi,sessionManager)
    }
}