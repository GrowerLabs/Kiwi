package com.example.projectkiwi.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.projectkiwi.LoginActivity
import com.example.projectkiwi.R
import com.example.projectkiwi.databinding.ActivityMainBinding
import com.example.projectkiwi.presentation.signup.SignupActivity
import com.example.projectkiwi.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        subscribeToCurrentUserEvents()
        mainViewModel.getCurrentUser()

        binding.btLogoutMainActivity.setOnClickListener {
            mainViewModel.logout()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()

        }
    }



   private fun subscribeToCurrentUserEvents()=lifecycleScope.launch {
       mainViewModel.currentUserState.collect { result->
           when(result){
               is Result.Success->{
                   hideProgressBar()
                   binding.btLogoutMainActivity.isVisible=true
                   binding.tvUsernameMainActivity.text=result.data?.username
               }

               is Result.Error->{
                   hideProgressBar()
                   binding.tvUsernameMainActivity.text=result.errorMessage

               }
               is Result.Loading->{
                   showProgressBar()
               }
           }

       }
   }
    //git version
    //git version 2
    private fun showProgressBar(){
        binding.pbLoadingMainActivity.isVisible=true
    }

    private fun hideProgressBar(){
        binding.pbLoadingMainActivity.isVisible=false
    }
}