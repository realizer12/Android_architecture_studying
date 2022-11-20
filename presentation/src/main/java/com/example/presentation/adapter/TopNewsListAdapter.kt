package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.databinding.ItemNewsListBinding
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.viewholder.TopNewArticleViewHolder

class TopNewsListAdapter:ListAdapter<ArticlePresentationDataModel, TopNewArticleViewHolder>(diffUtil) {

    lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewArticleViewHolder {
        val binding: ItemNewsListBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_news_list,
                parent,
                false)
        return TopNewArticleViewHolder(binding).apply {
            this.binding.root.setOnClickListener {
                itemClickListener.onTopNewItemClick(article = currentList[bindingAdapterPosition])
            }
        }
    }

    //아이템 클릭
    interface ItemClickListener{
        fun onTopNewItemClick(article: ArticlePresentationDataModel)//뉴스 기사 클릭시
    }

    fun setOnTopNewsItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: TopNewArticleViewHolder, position: Int) {
        holder.apply {
            bind(currentList[bindingAdapterPosition])
            binding.executePendingBindings()
        }
    }

    override fun getItemCount() = currentList.size

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ArticlePresentationDataModel>() {

            override fun areItemsTheSame(
                oldItem: ArticlePresentationDataModel,
                newItem: ArticlePresentationDataModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlePresentationDataModel,
                newItem: ArticlePresentationDataModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}