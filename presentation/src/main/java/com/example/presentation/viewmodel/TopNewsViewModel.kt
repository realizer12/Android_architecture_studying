package com.example.presentation.viewmodel

import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.fragment.TopNewsFragment
import com.example.presentation.model.ArticlePresentationDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

/**
 * main activity용 뷰모델이다.
**/
class TopNewsViewModel(
    private val topNewsRepository: TopNewsRepository
):BaseViewModel() {

    private var totalResult = TopNewsFragment.DEFAULT_LIST_SIZE
    private var page = 1

    val topNewsListPublishSubject: PublishSubject<List<ArticlePresentationDataModel>> = PublishSubject.create()

    init {
        getTopNewsList()
    }

    //탑 뉴스 리스트 가져오기
    private fun getTopNewsList() {

        topNewsRepository.getTopHeadLines(page = page, pageSize = com.example.util.const.Const.PageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val newTopNewsArticleList = it
                page++
                topNewsListPublishSubject.onNext(newTopNewsArticleList.map { it.fromArticleData() })
            },{
                topNewsListPublishSubject.onError(it)
            }).addDisposable()
    }


}