package com.example.presentation.viewmodel

import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * save fragment의 뷰모델
 **/
class SavedViewModel(
    private val topNewsRepository: TopNewsRepository
) : BaseViewModel() {

    //topnews는 최신 데이터 유지를 위해 behavior subject로 사용
    val savedTopNewsListBehaviorSubject: BehaviorSubject<List<ArticlePresentationDataModel>> =
        BehaviorSubject.create()

    //error 는 한번만 보여주면 되므로, publish를 사용한다.
    val errorPublishSubject: PublishSubject<Throwable> =
        PublishSubject.create()


    fun getSavedNewsList() {
        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                savedTopNewsListBehaviorSubject.onNext(articles.map { it.fromArticleData() })
            }, {
                errorPublishSubject.onNext(it)
            })
    }


}