package com.example.projectkiwi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.projectkiwi.databinding.ActivityLoginBinding
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

class LoginActivity : AppCompatActivity() {

    var imageArray: ArrayList<Int> = ArrayList()
    var carouselView: CarouselView? = null

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        imageArray.add(R.drawable.giveaway)
        imageArray.add(R.drawable.location)
        imageArray.add(R.drawable.payment)

        carouselView = findViewById(R.id.carouselView_login)

        carouselView!!.pageCount = imageArray.size
        carouselView!!.setImageListener(imageListener)

        binding.btnCreateAccountLogin.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.tvSignInLogin.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
    }

    var imageListener = ImageListener { position, imageView -> imageView.setImageResource(imageArray[position]) }

}