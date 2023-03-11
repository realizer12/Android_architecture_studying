package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.base.base.viewmodel.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.base.base.util.Event
import com.example.presentation.model.ArticlePresentationDataModel.Companion.fromArticleEntity
import com.realize.android.domain.usecase.GetTopHeadLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * save fragment의 뷰모델
 **/
@HiltViewModel
class SavedViewModel @Inject constructor(
    private val topHeadLinesUseCase: GetTopHeadLinesUseCase
) : BaseViewModel() {

    private val _savedTopNewsList = MutableLiveData<List<ArticlePresentationDataModel>>()
    val savedTopNewList: LiveData<List<ArticlePresentationDataModel>> = _savedTopNewsList

    fun getSavedNewsList() {
        //저장 여부 체크
        topHeadLinesUseCase(fromLocal = true)
            .subscribe({ articles ->
                _savedTopNewsList.value = articles.map { it.fromArticleEntity() }
            }, {
                _errorToast.value = Event(it)
            }).addDisposable()
    }


}