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
import com.example.nytarticles.RV_adapter.RVadapter_articles
import com.example.nytarticles.databinding.VotedArticlesBinding
import com.example.nytarticles.viewmodel.cnxVM
import com.example.nytarticles.viewmodel.cnxVMFactory
import com.example.nytarticles.viewmodel.nytVM


class VotedArticles : AppCompatActivity() {
    private lateinit var binding: VotedArticlesBinding
    private val types = listOf("AI","Tennis","Science","Politics")
    private lateinit var RVadapter_articles: RVadapter_articles
    private val viewModel: nytVM by viewModels()
    private val cnx_viewModel: cnxVM by viewModels { cnxVMFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VotedArticlesBinding.inflate(getLayoutInflater());
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
                cnx_viewModel.checkNetwork()
                cnx_viewModel.cnxIssue.observe(this@VotedArticles) { bool ->
                    if (bool == true) {
                        intent = Intent(this@VotedArticles, ErrorActivity::class.java)
                        intent.putExtra("error", "Please Check Your Internet Connection")
                        startActivity(intent)
                    } else {
                        val type = types[position]
                        binding.topAppBar.title = type + " Articles"
                        viewModel.getArticles(type)
                        viewModel.apiError.observe(this@VotedArticles) { res ->
                            println("nyaaah" + res)
                            if (res != "") {
                                intent = Intent(this@VotedArticles, ErrorActivity::class.java)
                                intent.putExtra("error", res)
                                startActivity(intent)
                            } else {

                                binding.recyclerView.layoutManager =
                                    LinearLayoutManager(this@VotedArticles)
                                viewModel.articles.observe(this@VotedArticles) { articles ->
                                    RVadapter_articles =
                                        RVadapter_articles(
                                            this@VotedArticles,
                                            articles.response.docs
                                        )
                                    binding.recyclerView.adapter = RVadapter_articles
                                }
                            }
                        }
                    }
                }
            }


            override fun onNothingSelected(parentView: AdapterView<*>) {
            }
        })

        binding.topAppBar.setNavigationOnClickListener {
            intent = Intent(this@VotedArticles, MainActivity::class.java)
            startActivity(intent)
        }


    }
}