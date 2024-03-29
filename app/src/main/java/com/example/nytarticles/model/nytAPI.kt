package com.example.nytarticles.model
import com.example.nytarticles.data.Article
import com.example.nytarticles.data.TopArticles
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface nytAPI {

    // Returns 10 articles of a certain type
    @GET("search/v2/articlesearch.json")
    fun getArticles(@Query("q") articleType: String,
                    @Query("api-key") apiKey: String = "AvcKcKs0FeyLf1jNhvg8s4ScsjhF12iT"
                    ): Call<Article>
    // Returns 20 Top Articles in the last week on a certain metric
    // metric can be "viewed" | "shared" | "emailed"
    @GET("mostpopular/v2/{metric}/7.json")
    fun getTopArticles(@Path("metric") metric: String,
                       @Query("api-key") apiKey: String = "AvcKcKs0FeyLf1jNhvg8s4ScsjhF12iT"
                       ): Call<TopArticles>

}


