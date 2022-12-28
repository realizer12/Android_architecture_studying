package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.realize.android.domain.repository.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import com.example.util.const.Const
import com.example.util.const.Const.PageSize
import com.realize.android.domain.usecase.GetTopHeadLinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * 카테고리별  탑뉴스 리스트용 뷰모델
 **/
@HiltViewModel
class CategoryTopNewsViewModel @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var isPagingFinish = false//페이징이 끝났는지 여부 체크
    private var page = 1//페이지

    //각 카테고리 별  top 뉴스 리스트
    private val tempCategoryTopNewList = mutableListOf<ArticlePresentationDataModel>()

    private val _categoryTopNewsListData = MutableLiveData<List<ArticlePresentationDataModel>>()
    val categoryTopNewsListData: LiveData<List<ArticlePresentationDataModel>> = _categoryTopNewsListData

    //category
    val categoryString = savedStateHandle.get<String>(Const.PARAM_ARTICLE_CATEGORY)

    init {
        getCategoryTopNewsList()
    }


    //탑 카테고리 뉴스 리스트 가져오기
    fun getCategoryTopNewsList() {
        //페이징 끝났을때  리턴해줌.
        if (isPagingFinish) {
            return
        }
        getTopHeadLinesUseCase(
            fromCategoryTopModel = true,
            page = page,
            pageSize = PageSize,
            category = categoryString
        ).subscribe({ newArticleList ->
            if (newArticleList.isNullOrEmpty()) {
                isPagingFinish = true
                return@subscribe
            }
            page++
            tempCategoryTopNewList.addAll(newArticleList.map { it.fromArticleEntity() })
            _categoryTopNewsListData.value = tempCategoryTopNewList.map { it.copy() }
        }, {
            _errorToast.value = Event(it)
        }).addDisposable()

    }


}