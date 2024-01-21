package com.example.nytarticles


import android.R
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.databinding.ActivityMainBinding
import com.example.nytarticles.viewmodel.nytVM


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val types = listOf("Politics", "Sport", "Science", "Android")
    private lateinit var RVadapter_articles: RVadapter_articles
    private val viewModel: nytVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel.getArticles("politics")
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
                binding.topAppBar.title = type + " Latest Articles"
                viewModel.getArticles(type.lowercase())
            }


            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.articles.observe(this) { articles ->
            RVadapter_articles = RVadapter_articles(this, articles.response.docs)
            binding.recyclerView.adapter = RVadapter_articles
        }

    }
}