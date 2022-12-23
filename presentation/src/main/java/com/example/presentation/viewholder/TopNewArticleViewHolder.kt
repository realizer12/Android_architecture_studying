package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.ItemNewsListBinding
import com.example.data.model.ArticleDataModel
import com.example.presentation.adapter.TopNewsListAdapter
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Util.checkTimePassed

class TopNewArticleViewHolder(
    val binding:ItemNewsListBinding,
    val adapter: TopNewsListAdapter
):RecyclerView.ViewHolder(binding.root) {
    fun bind(article: ArticlePresentationDataModel){
        binding.articleData = article
        binding.bindingAdapterPosition = bindingAdapterPosition
        binding.adapter = adapter
    }
}