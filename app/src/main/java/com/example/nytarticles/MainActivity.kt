package com.example.nytarticles


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nytarticles.databinding.ActivityMainBinding
import com.example.nytarticles.views.LatestArticles
import com.example.nytarticles.views.SearchArticles


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.latest.setOnClickListener{
            intent = Intent(this@MainActivity, LatestArticles::class.java)
            startActivity(intent)
        }

        binding.search.setOnClickListener{
            intent = Intent(this@MainActivity, SearchArticles::class.java)
            startActivity(intent)
        }
    }
}