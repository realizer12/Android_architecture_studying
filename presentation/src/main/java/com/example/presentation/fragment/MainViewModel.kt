package com.example.presentation.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.base.viewmodel.BaseViewModel
import com.example.presentation.R
import com.example.presentation.enums.MainEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

):BaseViewModel() {

    private val _currentPageType = MutableLiveData(MainEnum.TOP_NEWS)
    val currentPageType: LiveData<MainEnum> = _currentPageType

    fun setCurrentPage(menuItemId: Int): Boolean {
        val pageType = getPageType(menuItemId)
        changeCurrentPage(pageType)
        return true
    }

    private fun getPageType(menuItemId: Int): MainEnum {
        return when (menuItemId) {
            R.id.main_top_news_nav_graph -> MainEnum.TOP_NEWS
            R.id.main_categories_nav_graph -> MainEnum.CATEGORIES
            R.id.main_saved_nav_graph -> MainEnum.SAVED
            else -> _currentPageType.value?:MainEnum.TOP_NEWS
        }
    }

    private fun changeCurrentPage(pageType: MainEnum) {
        if (currentPageType.value == pageType) return
        _currentPageType.value = pageType
    }
}