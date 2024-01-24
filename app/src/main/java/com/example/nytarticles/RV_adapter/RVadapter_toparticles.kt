package com.example.nytarticles.RV_adapter;

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nytarticles.R
import com.example.nytarticles.data.Result


class RVadapter_toparticles(private val context: Context, articles: List<Result>) :
    RecyclerView.Adapter<RVadapter_toparticles.ViewHolder>() {
    private val articles: List<Result>
    private  var increment = 1

    init {
        this.articles = articles
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var description: TextView
        var url: TextView
        var author : TextView
        var title : TextView
        var publication : TextView
        var topic : TextView

        init {
            description = itemView.findViewById<TextView>(R.id.description)
            url = itemView.findViewById<TextView>(R.id.url)
            author = itemView.findViewById<TextView>(R.id.author)
            title = itemView.findViewById<TextView>(R.id.titleart)
            publication = itemView.findViewById<TextView>(R.id.published)
            topic = itemView.findViewById<TextView>(R.id.topic)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.toparticle, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article: Result = articles[position]
        holder.description.text = article.abstract
        holder.url.text = article.url
        holder.url.setOnClickListener {
            // Handle the click event, for example, open the link in a browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(holder.url.text.toString()))
            holder.itemView.context.startActivity(intent)
        }
        holder.author.text = article.byline
        if (holder.author.text != "")
            holder.author.text = holder.author.text.substring(3)
        holder.publication.text = article.updated.substring(0,10)
        holder.title.text = article.title
        holder.topic.text = article.des_facet.take(3).joinToString("\n")
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}