package com.example.projectkiwi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.projectkiwi.presentation.home.MainActivity
import com.example.projectkiwi.presentation.home.MainViewModel
import com.example.projectkiwi.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()




        Handler().postDelayed({
            subscribeToCurrentUserEvents()
            mainViewModel.getCurrentUser()
        },2500)
    }

    private fun subscribeToCurrentUserEvents()=lifecycleScope.launch {
        mainViewModel.currentUserState.collect { result->
            when(result){
                is Result.Success->{

                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }

                is Result.Error->{
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()

                }
                is Result.Loading->{
                       //
                }
            }

        }
    }
}