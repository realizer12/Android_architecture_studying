package com.example.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.util.const.Const
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

/**
 * 게시글 상세화면용 뷰모델
**/
class ArticleDetailViewModel(
    private val topNewsRepository: TopNewsRepository,
    private val savedStateHandle: SavedStateHandle
):BaseViewModel() {

    //article 데이터 넘겨 받음.
    private val detailArticleModel = savedStateHandle?.get<ArticlePresentationDataModel>(Const.PARAM_ARTICLE_MODEL)


    //topnews는 최신 데이터 유지를 위해 behavior subject로 사용
    val isSaveArticle: BehaviorSubject<Boolean> =
        BehaviorSubject.create()

    //error 는 한번만 보여주면 되므로, publish를 사용한다.
    val errorPublishSubject: PublishSubject<Throwable> =
        PublishSubject.create()

    init {
        checkSavedArticle()
    }


    //저장한 article 인지 여부를 체크 한다.
    private fun checkSavedArticle() {
        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ saveArticleList ->
                isSaveArticle.onNext(saveArticleList.any { it.title == detailArticleModel?.title && it.publishedAt == detailArticleModel?.publishedAt && it.url == detailArticleModel?.url })
            }, {
                errorPublishSubject.onNext(it)
            })
    }


//    //게시글 저장 취소
//    fun unSaveArticle(){
//        topNewsRepository.removeArticle(article = article?.toArticleData()?:return)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                setSaveIconVisible(isSaveStatus = false)
//            }, {
//                showToast(it.message.toString())
//            })
//    }


    //게시물 저장
    fun saveArticle(){

    }
}