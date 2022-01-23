package com.example.projectkiwi.presentation.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.projectkiwi.databinding.ActivitySignupBinding
import com.example.projectkiwi.presentation.signin.SigninActivity
import com.example.projectkiwi.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signUpViewModel: SignUpViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvSignin.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        subscribeToRegisterEvent()
        binding.btnSignup.setOnClickListener {
            val username=binding.etFirstNameSignup.text.toString().trim()
            val email=binding.etEmailIdSignup.text.toString().trim()
            val password=binding.etPasswordSignup.text.toString().trim()

            signUpViewModel.createUser(username, email, password)



        }


    }
    private fun subscribeToRegisterEvent()=lifecycleScope.launch {
               signUpViewModel.registerState.collect {result->
                   when(result){
                       is Result.Success->{
                            hideProgressBar()
                           Toast.makeText(this@SignupActivity,"Account created successfully",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignupActivity,SignupActivity::class.java))
                            finish()
                       }
                       is Result.Loading->{
                             showProgressBar()


                       }
                       is Result.Error->{
                            hideProgressBar()
                           Toast.makeText(this@SignupActivity,result.errorMessage,Toast.LENGTH_SHORT).show()


                       }
                   }

               }
    }


    private fun showProgressBar(){
        binding.pbLoadingSignupActivity.isVisible=true
    }

    private fun hideProgressBar(){
        binding.pbLoadingSignupActivity.isVisible=false
    }
}