package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import com.example.util.const.Const
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * 게시글 상세화면용 뷰모델
 **/
class ArticleDetailViewModel(
    private val topNewsRepository: TopNewsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    //article 데이터 넘겨 받음.
    private val detailArticleModel =
        savedStateHandle.get<ArticlePresentationDataModel>(Const.PARAM_ARTICLE_MODEL)

    //topnews는 최신 데이터 유지를 위해 behavior subject로 사용
    private val _isSaveArticle = MutableLiveData<Boolean>()
    val isSaveArticle:LiveData<Boolean> = _isSaveArticle

    private val _detailArticle = MutableLiveData<ArticlePresentationDataModel>()
    val detailArticle:LiveData<ArticlePresentationDataModel> = _detailArticle

    private val _errorToast = MutableLiveData<Event<Throwable>>()
    val errorToast:LiveData<Event<Throwable>> = _errorToast



    init {

        detailArticleModel?.let {
            _detailArticle.value = it
        }

        checkSavedArticle()

    }


    //저장한 article 인지 여부를 체크 한다.
    private fun checkSavedArticle() {
        //저장 여부 체크
        topNewsRepository.getSavedArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ saveArticleList ->
                _isSaveArticle.value = saveArticleList.any { it.title == detailArticleModel?.title && it.publishedAt == detailArticleModel?.publishedAt && it.url == detailArticleModel?.url }
            }, {
                _errorToast.value = Event(it)
            })
    }


    //게시글 저장 취소
    fun unSaveArticle() {
        if (detailArticleModel == null) {
            return
        }
        topNewsRepository.removeArticle(article = detailArticleModel.toArticleData())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isSaveArticle.value = false
            }, {
                _errorToast.value = Event(it)
            })
    }


    //게시물 저장
    fun saveArticle() {
        if (detailArticleModel == null) {
            return
        }
        topNewsRepository.saveArticle(article = detailArticleModel.toArticleData())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isSaveArticle.value = true
            }, {
                _errorToast.value = Event(it)
            })
    }
}