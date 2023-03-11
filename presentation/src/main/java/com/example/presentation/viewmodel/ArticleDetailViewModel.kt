package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.base.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.base.base.Event
import com.example.presentation.model.ArticlePresentationDataModel.Companion.toArticleEntity
import com.example.util.const.Const
import com.realize.android.domain.usecase.CheckSavedArticleUseCase
import com.realize.android.domain.usecase.RemoveArticleUseCase
import com.realize.android.domain.usecase.SaveArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 게시글 상세화면용 뷰모델
 **/
@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val saveArticleUseCase: SaveArticleUseCase,
    private val removeArticleUseCase: RemoveArticleUseCase,
    private val checkSavedArticleUseCase: CheckSavedArticleUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(){

    //article 데이터 넘겨 받음.
    private val detailArticleModel =
        savedStateHandle.get<ArticlePresentationDataModel>(Const.PARAM_ARTICLE_MODEL)

    private val _isSaveArticle = MutableLiveData<Boolean>()
    val isSaveArticle:LiveData<Boolean> = _isSaveArticle

    private val _detailArticle = MutableLiveData<ArticlePresentationDataModel>()
    val detailArticle:LiveData<ArticlePresentationDataModel> = _detailArticle

    init {

        detailArticleModel?.let {
            _detailArticle.value = it
        }

        checkSavedArticle()

    }


    //저장한 article 인지 여부를 체크 한다.
    fun checkSavedArticle() {
        //저장 여부 체크
        checkSavedArticleUseCase(
            articleDataEntity = detailArticleModel?.toArticleEntity()
        ).subscribe({
                _isSaveArticle.value = it
            }, {
                _errorToast.value = Event(it)
            }).addDisposable()
    }


    //게시글 저장 취소
    fun unSaveArticle() {
        removeArticleUseCase(
            article = detailArticleModel?.toArticleEntity()
        ).subscribe({
            _isSaveArticle.value = false
        }, {
            _errorToast.value = Event(it)
        }).addDisposable()
    }


    //게시물 저장
    fun saveArticle() {
        saveArticleUseCase(article = detailArticleModel?.toArticleEntity())
            .subscribe({
                _isSaveArticle.value = true
            }, {
                _errorToast.value = Event(it)
            }).addDisposable()
    }
}