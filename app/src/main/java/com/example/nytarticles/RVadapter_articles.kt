package com.example.nytarticles;

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nytarticles.data.Doc


class RVadapter_articles(private val context: Context, articles: List<Doc>) :
    RecyclerView.Adapter<RVadapter_articles.ViewHolder>() {
    private val articles: List<Doc>

    init {
        this.articles = articles
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView
        var url: TextView
        var author : TextView
        var title : TextView
        var publication : TextView

        init {
            description = itemView.findViewById<TextView>(R.id.description)
            url = itemView.findViewById<TextView>(R.id.url)
            author = itemView.findViewById<TextView>(R.id.author)
            title = itemView.findViewById<TextView>(R.id.titleart)
            publication = itemView.findViewById<TextView>(R.id.published)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Doc = articles[position]
        holder.description.text = article.abstract
        holder.url.text = article.web_url
        holder.url.setOnClickListener {
            // Handle the click event, for example, open the link in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(holder.url.text.toString()))
            holder.itemView.context.startActivity(intent)
        }
        holder.author.text = article.byline.original
        if (holder.author.text != "")
            holder.author.text = holder.author.text.substring(3)
        holder.publication.text = article.pub_date.substring(0,10)
        holder.title.text = article.headline.main
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}