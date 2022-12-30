package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import com.example.util.const.Const.PageSize
import com.realize.android.domain.usecase.GetTopHeadLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * main activity용 뷰모델이다.
 **/
@HiltViewModel
class TopNewsViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase
) : BaseViewModel() {

    private var isPagingFinish = false
    private var page = 1

    private val tempTopNewsList = mutableListOf<ArticlePresentationDataModel>()

    private val _topNewsListData = MutableLiveData<List<ArticlePresentationDataModel>>()
    val topNewsListData :LiveData<List<ArticlePresentationDataModel>> = _topNewsListData

    init {
        getTopNewList()
    }

    //탑 뉴스 리스트 가져오기
    fun getTopNewList() {

        //페이징 끝났을때  리턴해줌.
        if (isPagingFinish) {
            return
        }

        getTopHeadLinesUseCase(page = page, pageSize = PageSize)
            .subscribe({ newArticleList ->
                if (newArticleList.isNullOrEmpty()) {
                    isPagingFinish = true
                    return@subscribe
                }
                 page++
                 tempTopNewsList.addAll(newArticleList.map { it.fromArticleEntity() })
                _topNewsListData.value = tempTopNewsList.map { it.copy() }
            }, {
                _errorToast.value = Event(it)
            }).addDisposable()
    }


}