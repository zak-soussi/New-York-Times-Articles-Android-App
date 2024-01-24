package com.example.nytarticles.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nytarticles.databinding.ErrorActivityBinding

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ErrorActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ErrorActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.errormsg.text = intent.getStringExtra("error")
        binding.retryButton.setOnClickListener{
            intent = Intent(this@ErrorActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}