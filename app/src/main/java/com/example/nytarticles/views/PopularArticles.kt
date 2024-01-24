package com.example.nytarticles.views


import android.R
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.RV_adapter.RVadapter_toparticles
import com.example.nytarticles.databinding.ActivityPopularArticlesBinding
import com.example.nytarticles.viewmodel.nytVM


class PopularArticles : AppCompatActivity() {
    private lateinit var binding: ActivityPopularArticlesBinding
    private val types = listOf("Viewed","Shared","Emailed")
    private lateinit var RVadapter_toparticles: RVadapter_toparticles
    private val viewModel: nytVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                val type = types[position].lowercase()
                binding.topAppBar.title ="This week most " + type
                viewModel.getTopArticles(type)
            }


            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })
        viewModel.getTopArticles("viewed")
        binding.topAppBar.setNavigationOnClickListener {
            intent = Intent(this@PopularArticles, MainActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.topArticles.observe(this) { articles ->
            RVadapter_toparticles = RVadapter_toparticles(this, articles.results)
            binding.recyclerView.adapter = RVadapter_toparticles
        }

    }
}