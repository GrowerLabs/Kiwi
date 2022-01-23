package com.example.projectkiwi.presentation.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.projectkiwi.ForgotActivity
import com.example.projectkiwi.presentation.signup.SignupActivity
import com.example.projectkiwi.databinding.ActivitySigninBinding
import com.example.projectkiwi.presentation.home.MainActivity
import com.example.projectkiwi.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SigninActivity : AppCompatActivity() {

    private val signInViewModel:SignInViewModel by viewModels()

    private lateinit var binding: ActivitySigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        //disable sigin button if user has not given input in username and password field
        binding.btnSignin.isEnabled=false


        //enable sigin button if user has given input in username and password field

        binding.etPasswordSignIn.addTextChangedListener {
            val username=binding.etEmailIdSignIn.text.toString().trim()
            binding.btnSignin.isEnabled=(username.isNotEmpty() && it.toString().isNotEmpty())
        }
          subscribeToLoginEvent()
        binding.btnSignin.setOnClickListener {
            val username=binding.etEmailIdSignIn.text.toString().trim()

            val password=binding.etPasswordSignIn.text.toString().trim()
            signInViewModel.loginUser(username,password)
        }




        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun subscribeToLoginEvent()=lifecycleScope.launch {
        signInViewModel.loginState.collect {result->
            when(result){
                is Result.Success->{
                    hideProgressBar()
                    Toast.makeText(this@SigninActivity,"Account login successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SigninActivity,MainActivity::class.java))
                    finish()
                }
                is Result.Loading->{
                    showProgressBar()


                }
                is Result.Error->{
                    hideProgressBar()
                    Toast.makeText(this@SigninActivity,result.errorMessage, Toast.LENGTH_SHORT).show()


                }
            }

        }
    }



    private fun showProgressBar(){
        binding.pbLoadingSignInActivity.isVisible=true
    }

    private fun hideProgressBar(){
        binding.pbLoadingSignInActivity.isVisible=false
    }


}