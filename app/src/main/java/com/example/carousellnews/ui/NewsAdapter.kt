package com.example.carousellnews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carousellnews.data.model.NewsResponseModelItem
import com.example.carousellnews.databinding.NewsItemLayoutBinding
import com.example.carousellnews.utils.Utility

class NewsAdapter : ListAdapter<NewsResponseModelItem, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class NewsViewHolder(private val binding: NewsItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsResponseModelItem) {
            binding.apply {
                tvNewsTitle.text = newsItem.title
                tvNewsDescription.text = newsItem.description
                tvNewsCreationDate.text = Utility.readableFormatOfData(newsItem.timeCreated)
                Glide.with(ivNews).load(newsItem.bannerUrl).into(ivNews)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsResponseModelItem>() {
        override fun areItemsTheSame(
            oldItem: NewsResponseModelItem,
            newItem: NewsResponseModelItem
        ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: NewsResponseModelItem,
            newItem: NewsResponseModelItem
        ) =
            oldItem == newItem
    }
}