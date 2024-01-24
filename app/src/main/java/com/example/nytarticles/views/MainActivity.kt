package com.example.nytarticles.views


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nytarticles.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.latest.setOnClickListener{
            intent = Intent(this@MainActivity, VotedArticles::class.java)
            startActivity(intent)
        }

        binding.search.setOnClickListener{
            intent = Intent(this@MainActivity, SearchArticles::class.java)
            startActivity(intent)
        }

        binding.popular.setOnClickListener{
            intent = Intent(this@MainActivity, PopularArticles::class.java)
            startActivity(intent)
        }
    }
}