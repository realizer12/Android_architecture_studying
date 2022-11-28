package com.example.presentation.viewmodel.factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.viewmodel.ArticleDetailViewModel
import com.example.presentation.viewmodel.TopNewsViewModel

class ViewModelFactory(
    private val repository: TopNewsRepository,
    private val savedStateHandle: SavedStateHandle
 ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TopNewsViewModel::class.java) -> {
                TopNewsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ArticleDetailViewModel::class.java) -> {
                ArticleDetailViewModel(repository,savedStateHandle) as T
            }
            else -> {
                throw Exception("cannot create viewModel")
            }
        }
    }
}


