package com.example.nytarticles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nytarticles.viewmodel.SingleLiveEvent
import com.example.nytarticles.data.Article
import com.example.nytarticles.data.TopArticles
import com.example.nytarticles.model.nytRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class nytVM : ViewModel() {

    private val _articles = SingleLiveEvent<Article>()
    val articles: LiveData<Article> get() = _articles

    private val _apiError = SingleLiveEvent<String>()
    val apiError: LiveData<String> get() = _apiError

    private val _topArticles = SingleLiveEvent<TopArticles>()
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
                    _apiError.postValue("")
                } else {
                    _apiError.postValue("Failed to fetch data.\n Please try again.")
                }
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                _apiError.postValue("Failed to fetch data.\n Please try again.")
            }
        })
    }


    fun getTopArticles(metric: String) {
        val call = nytRetrofit.retrofitService.getTopArticles(metric)
        call.enqueue(object : Callback<TopArticles> {
            override fun onResponse(
                call: Call<TopArticles>,
                response: Response<TopArticles>
            ) {
                if (response.isSuccessful) {
                    _topArticles.postValue(response.body())
                    _apiError.postValue("")
                } else {
                    _apiError.postValue("Failed to fetch data.\n Please try again.")
                }
            }

            override fun onFailure(call: Call<TopArticles>, t: Throwable) {
                _apiError.postValue(
                    "Failed to fetch data.\n" +
                            " Please try again."
                )
            }
        })
    }
}
