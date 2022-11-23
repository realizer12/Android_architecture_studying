package com.example.presentation.viewmodel

import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel

/**
 * main activity용 뷰모델이다.
**/
class MainViewModel(
    private val repository: TopNewsRepository
):BaseViewModel() {
    init {

    }

}