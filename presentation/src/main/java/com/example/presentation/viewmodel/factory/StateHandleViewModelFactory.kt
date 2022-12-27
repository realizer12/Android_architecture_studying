package com.example.presentation.viewmodel.factory

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.realize.android.domain.repository.TopNewsRepository
import com.example.presentation.viewmodel.ArticleDetailViewModel
import com.example.presentation.viewmodel.CategoryTopNewsViewModel

class StateHandleViewModelFactory(
    private val repository: TopNewsRepository
) : AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return when {
            modelClass.isAssignableFrom(ArticleDetailViewModel::class.java) -> {
                ArticleDetailViewModel(repository, handle) as T
            }
            modelClass.isAssignableFrom(CategoryTopNewsViewModel::class.java) -> {
                CategoryTopNewsViewModel(repository,handle) as T
            }
            else -> {
                throw Exception("cannot create viewModel")
            }
        }
    }
}