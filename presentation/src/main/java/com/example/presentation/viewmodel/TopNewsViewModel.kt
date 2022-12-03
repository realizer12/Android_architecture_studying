package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import com.example.util.const.Const.PageSize
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * main activity용 뷰모델이다.
 **/
class TopNewsViewModel(
    private val topNewsRepository: TopNewsRepository
) : BaseViewModel() {

    private var isPagingFinish = false
    private var page = 1

    private val tempTopNewsList = mutableListOf<ArticlePresentationDataModel>()

    private val _topNewsList = MutableLiveData<List<ArticlePresentationDataModel>>()
    val topNewsList:LiveData<List<ArticlePresentationDataModel>> = _topNewsList

    init {
        getTopNewsList()
    }

    //탑 뉴스 리스트 가져오기
    fun getTopNewsList() {

        //페이징 끝났을때  리턴해줌.
        if (isPagingFinish) {
            return
        }

        topNewsRepository.getTopHeadLines(page = page, pageSize = PageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newArticleList ->
                if (newArticleList.isNullOrEmpty()) {
                    isPagingFinish = true
                    return@subscribe
                }

                page++
                tempTopNewsList.addAll(newArticleList.map { it.fromArticleData() })
                _topNewsList.value = tempTopNewsList.map { it.copy() }
            }, {
                _errorToast.value = Event(it)
            }).addDisposable()
    }


}