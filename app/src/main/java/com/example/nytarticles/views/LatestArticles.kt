package com.example.nytarticles.views


import android.R
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.MainActivity
import com.example.nytarticles.RVadapter_articles
import com.example.nytarticles.databinding.ActivityLatestArticlesBinding
import com.example.nytarticles.viewmodel.nytVM
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class LatestArticles : AppCompatActivity() {
    private lateinit var binding: ActivityLatestArticlesBinding
    private val types = listOf("AI","Tennis","Science","Politics")
    private lateinit var RVadapter_articles: RVadapter_articles
    private val viewModel: nytVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLatestArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel.getArticles("AI")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.Spinner.adapter = adapter
        binding.Spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val type = types[position]
                binding.topAppBar.title = type + "Articles"
                viewModel.getArticles(type)
            }


            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })

        binding.topAppBar.setNavigationOnClickListener {
            intent = Intent(this@LatestArticles, MainActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerView.itemAnimator = SlideInUpAnimator()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.articles.observe(this) { articles ->
            RVadapter_articles = RVadapter_articles(this, articles.response.docs)
            binding.recyclerView.adapter = RVadapter_articles
        }

    }
}