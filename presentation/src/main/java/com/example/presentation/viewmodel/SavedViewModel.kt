package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * save fragment의 뷰모델
 **/
class SavedViewModel(
    private val topNewsRepository: TopNewsRepository
) : BaseViewModel() {

    private val _savedTopNewsList = MutableLiveData<List<ArticlePresentationDataModel>>()
    val savedTopNewList: LiveData<List<ArticlePresentationDataModel>> = _savedTopNewsList

    fun getSavedNewsList() {
        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                _savedTopNewsList.value = articles.map { it.fromArticleData() }
            }, {
                _errorToast.value = Event(it)
            })
    }


}