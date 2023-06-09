package com.themafia.apps.newsx.presentation.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themafia.apps.newsx.R
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.retrofit.dataclasses.Source
import com.themafia.apps.newsx.databinding.NewsListItemBinding

class Adapter(var location: String) : RecyclerView.Adapter<Adapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>(){

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this , callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount()  = differ.currentList.size

    inner class NewsViewHolder(private val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root){
        lateinit var selected_article : Article
        fun bind(article: Article){

            binding.tvDescription.text = article.description
            binding.tvSource.text = article.source?.name
            binding.tvTitle.text = article.title
            binding.tvPublishedAt.text = article.publishedAt

            Glide.with(binding.ivArticleImage.context).load(article.urlToImage).into(binding.ivArticleImage)

            binding.root.setOnClickListener {
                Toast.makeText(binding.linLayout.context, "Loading...", Toast.LENGTH_SHORT).show()

                selected_article = if(article.source!!.id == null){
                    Article(
                        article.id ,
                        article.author ,
                        article.content ,
                        article.description ,
                        article.publishedAt ,
                        source = Source( article.source.name.lowercase(), article.source.name),
                        article.title,
                        article.url,
                        article.urlToImage
                    )
                }else{
                    article
                }

                val bundle = bundleOf("news_url" to article.url , "selected_article" to selected_article)

                if(location == "savedNews"){
                    it.findNavController().navigate(R.id.action_savedNewsFragment_to_newsDetailFragment , bundle)
                }else if(location == "news"){
                    it.findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment , bundle)
                }

            }
        }
    }
}