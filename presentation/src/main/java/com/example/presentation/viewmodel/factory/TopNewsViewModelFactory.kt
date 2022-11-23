package com.example.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.viewmodel.MainViewModel

class MainViewModelFactory(
    private val repository: TopNewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            else -> {
                throw Exception("cannot create viewModel")
            }
        }
    }
}