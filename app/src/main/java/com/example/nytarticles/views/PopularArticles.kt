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
import com.example.nytarticles.RV_adapter.RVadapter_toparticles
import com.example.nytarticles.databinding.ActivityPopularArticlesBinding
import com.example.nytarticles.viewmodel.cnxVM
import com.example.nytarticles.viewmodel.cnxVMFactory
import com.example.nytarticles.viewmodel.nytVM


class PopularArticles : AppCompatActivity() {
    private lateinit var binding: ActivityPopularArticlesBinding
    private val types = listOf("Viewed", "Shared", "Emailed")
    private lateinit var RVadapter_toparticles: RVadapter_toparticles
    private val viewModel: nytVM by viewModels()
    private val cnx_viewModel: cnxVM by viewModels { cnxVMFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularArticlesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, types)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.Spinner.adapter = adapter
        viewModel.getTopArticles("viewed")
        binding.topAppBar.title = "This week most viewed"
        viewModel.apiError.observe(this@PopularArticles) { res ->
            if (res != "") {
                intent = Intent(this@PopularArticles, ErrorActivity::class.java)
                intent.putExtra("error", res)
                startActivity(intent)
            } else {

                binding.recyclerView.layoutManager =
                    LinearLayoutManager(this@PopularArticles)
                viewModel.topArticles.observe(this@PopularArticles) { articles ->
                    RVadapter_toparticles =
                        RVadapter_toparticles(
                            this@PopularArticles,
                            articles.results
                        )
                    binding.recyclerView.adapter = RVadapter_toparticles
                }
            }
        }



        binding.Spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                cnx_viewModel.checkNetwork()
                cnx_viewModel.cnxIssue.observe(this@PopularArticles) { bool ->
                    if (bool == true) {
                        intent = Intent(this@PopularArticles, ErrorActivity::class.java)
                        intent.putExtra("error", "Please Check Your Internet Connection")
                        startActivity(intent)
                    } else {
                        val type = types[position].lowercase()
                        binding.topAppBar.title = "This week most " + type
                        viewModel.getTopArticles(type)
                        viewModel.apiError.observe(this@PopularArticles) { res ->
                            if (res != "") {
                                intent = Intent(this@PopularArticles, ErrorActivity::class.java)
                                intent.putExtra("error", res)
                                startActivity(intent)
                            } else {

                                binding.recyclerView.layoutManager =
                                    LinearLayoutManager(this@PopularArticles)
                                viewModel.topArticles.observe(this@PopularArticles) { articles ->
                                    RVadapter_toparticles =
                                        RVadapter_toparticles(
                                            this@PopularArticles,
                                            articles.results
                                        )
                                    binding.recyclerView.adapter = RVadapter_toparticles
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
            intent = Intent(this@PopularArticles, MainActivity::class.java)
            startActivity(intent)
        }
    }
}