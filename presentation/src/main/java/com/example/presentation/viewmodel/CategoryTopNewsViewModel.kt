package com.example.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.data.repository.news.TopNewsRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.util.const.Const
import com.example.util.const.Const.PageSize
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * 카테고리별  탑뉴스 리스트용 뷰모델
 **/
class CategoryTopNewsViewModel(
    private val topNewsRepository: TopNewsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private var isPagingFinish = false//페이징이 끝났는지 여부 체크
    private var page = 1//페이지

    //각 카테고리 별  top 뉴스 리스트
    private val tempCategoryTopNewList = mutableListOf<ArticlePresentationDataModel>()

    //topnews는 최신 데이터 유지를 위해 behavior subject로 사용
    val categoryTopNewsListBehaviorSubject: BehaviorSubject<List<ArticlePresentationDataModel>> =
        BehaviorSubject.create()

    //error 는 한번만 보여주면 되므로, publish를 사용한다.
    val errorPublishSubject: PublishSubject<Throwable> =
        PublishSubject.create()

    //category
    val categoryString = savedStateHandle.get<String>(Const.PARAM_ARTICLE_CATEGORY)

    init {
        getCategoryTopNewsList()
    }


    //탑 카테고리 뉴스 리스트 가져오기
    fun getCategoryTopNewsList() {

        //카테고리가 없을떄 early 리턴을 시켜준다.
        if(categoryString.isNullOrEmpty()){
            errorPublishSubject.onNext(Throwable("카테고리가 없어요 ㅠㅠ"))
            return
        }

        //페이징 끝났을때  리턴해줌.
        if (isPagingFinish) {
            return
        }

        //카테고리별 topnews 리스트를 가져오낟.
        topNewsRepository.getTopHeadLines(
            page = page,
            pageSize = PageSize,
            category = categoryString
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ newArticleList ->

                if (newArticleList.isNullOrEmpty()) {
                    isPagingFinish = true
                    return@subscribe
                }
                page++
                tempCategoryTopNewList.addAll(newArticleList.map { it.fromArticleData() })
                categoryTopNewsListBehaviorSubject.onNext(tempCategoryTopNewList.map { it.copy() })

            }, {
                errorPublishSubject.onNext(it)
            }).addDisposable()
    }


}