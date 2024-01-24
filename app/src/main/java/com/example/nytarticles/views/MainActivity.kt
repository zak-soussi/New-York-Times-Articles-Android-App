package com.example.nytarticles.views


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.nytarticles.databinding.ActivityMainBinding
import com.example.nytarticles.viewmodel.cnxVM
import com.example.nytarticles.viewmodel.cnxVMFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val cnx_viewModel: cnxVM by viewModels { cnxVMFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.latest.setOnClickListener {
            cnx_viewModel.checkNetwork()
            cnx_viewModel.cnxIssue.observe(this@MainActivity) { bool ->
                if (bool == true) {
                    intent = Intent(this@MainActivity, ErrorActivity::class.java)
                    intent.putExtra("error", "Please Check Your Internet Connection")
                    startActivity(intent)
                } else {
                    intent = Intent(this@MainActivity, VotedArticles::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.search.setOnClickListener {
            cnx_viewModel.checkNetwork()
            cnx_viewModel.cnxIssue.observe(this@MainActivity) { bool ->
                if (bool == true) {
                    intent = Intent(this@MainActivity, ErrorActivity::class.java)
                    intent.putExtra("error", "Please Check Your Internet Connection")
                    startActivity(intent)
                } else {
                    intent = Intent(this@MainActivity, SearchArticles::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.popular.setOnClickListener {
            cnx_viewModel.checkNetwork()
            cnx_viewModel.cnxIssue.observe(this@MainActivity) { bool ->
                if (bool == true) {
                    intent = Intent(this@MainActivity, ErrorActivity::class.java)
                    intent.putExtra("error", "Please Check Your Internet Connection")
                    startActivity(intent)
                } else {
                    intent = Intent(this@MainActivity, PopularArticles::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}