package com.example.nytarticles.views


import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.RV_adapter.RVadapter_articles
import com.example.nytarticles.databinding.ActivitySearchArticlesBinding
import com.example.nytarticles.viewmodel.cnxVM
import com.example.nytarticles.viewmodel.cnxVMFactory
import com.example.nytarticles.viewmodel.nytVM


class SearchArticles : AppCompatActivity() {
    private lateinit var binding: ActivitySearchArticlesBinding
    private lateinit var RVadapter_articles: RVadapter_articles
    private val viewModel: nytVM by viewModels()
    private val cnx_viewModel: cnxVM by viewModels { cnxVMFactory(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.searchButton.setOnClickListener {
            cnx_viewModel.checkNetwork()
            cnx_viewModel.cnxIssue.observe(this) { bool ->
                if (bool == true) {
                    intent = Intent(this@SearchArticles, ErrorActivity::class.java)
                    intent.putExtra("error", "Please Check Your Internet Connection")
                    startActivity(intent)
                } else {

                    binding.topAppBar.title = binding.editText.text.toString() + " Articles"
                    viewModel.getArticles(binding.editText.text.toString())
                    binding.editText.text.clear()
                    viewModel.apiError.observe(this) { res ->
                        if (res != "") {
                            intent = Intent(this@SearchArticles, ErrorActivity::class.java)
                            intent.putExtra("error", res)
                            startActivity(intent)
                        } else {

                            binding.recyclerView.layoutManager = LinearLayoutManager(this)
                            viewModel.articles.observe(this) { articles ->
                                RVadapter_articles =
                                    RVadapter_articles(this, articles.response.docs)
                                binding.recyclerView.adapter = RVadapter_articles
                            }
                        }
                    }
                }
            }
        }


        binding.topAppBar.setNavigationOnClickListener {
            intent = Intent(this@SearchArticles, MainActivity::class.java)
            startActivity(intent)
        }

    }
}