package com.example.presentation.viewmodel

import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
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

    //topnews는 최신 데이터 유지를 위해 behavior subject로 사용
    val topNewsListBehaviorSubject: BehaviorSubject<List<ArticlePresentationDataModel>> =
        BehaviorSubject.create()

    //error 는 한번만 보여주면 되므로, publish를 사용한다.
    val errorPublishSubject: PublishSubject<Throwable> =
        PublishSubject.create()

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
                topNewsListBehaviorSubject.onNext(tempTopNewsList.map { it.copy() })
            }, {
                errorPublishSubject.onNext(it)
            }).addDisposable()
    }


}