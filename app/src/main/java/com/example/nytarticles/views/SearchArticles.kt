package com.example.nytarticles.views


import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.MainActivity
import com.example.nytarticles.RVadapter_articles
import com.example.nytarticles.databinding.ActivitySearchArticlesBinding
import com.example.nytarticles.viewmodel.nytVM


class SearchArticles : AppCompatActivity() {
    private lateinit var binding: ActivitySearchArticlesBinding

    private lateinit var RVadapter_articles: RVadapter_articles
    private val viewModel: nytVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchButton.setOnClickListener {
            val type = binding.editText.text.toString()
            if (type != "") {
                binding.editText.text.clear()
                binding.topAppBar.title = type + "Articles"
                viewModel.getArticles(type)
            } else {
                Toast.makeText(this, "You have to type something", Toast.LENGTH_LONG).show()
            }
        }


        binding.topAppBar.setNavigationOnClickListener {
            intent = Intent(this@SearchArticles, MainActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.articles.observe(this) { articles ->
            RVadapter_articles = RVadapter_articles(this, articles.response.docs)
            binding.recyclerView.adapter = RVadapter_articles
        }

    }
}