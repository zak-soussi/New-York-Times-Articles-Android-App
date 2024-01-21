package com.example.nytarticles.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nytarticles.data.Article
import com.example.nytarticles.data.TopArticles
import com.example.nytarticles.model.metric
import com.example.nytarticles.model.nytRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class nytVM : ViewModel() {

    private val _articles = MutableLiveData<Article>()
    val articles: LiveData<Article> get() = _articles

    private val _topArticles = MutableLiveData<TopArticles>()
    val topArticles: LiveData<TopArticles> get() = _topArticles

    fun getArticles(articleType: String) {
        val call = nytRetrofit.retrofitService.getArticles(articleType)
        call.enqueue(object : Callback<Article> {
            override fun onResponse(
                call: Call<Article>,
                response: Response<Article>
            ) {
                if (response.isSuccessful) {
                    _articles.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                Log.e("Error", "Call Failed", t)
            }
        })
    }


    fun getTopArticles(metric: metric) {
        val call = nytRetrofit.retrofitService.getTopArticles(metric)
        call.enqueue(object : Callback<TopArticles> {
            override fun onResponse(call: Call<TopArticles>, response: Response<TopArticles>) {
                if (response.isSuccessful) {
                    _topArticles.postValue(response.body())

                }
            }

            override fun onFailure(call: Call<TopArticles>, t: Throwable) {
                Log.e("Error", "Call Failed", t)
            }
        })
    }
}
