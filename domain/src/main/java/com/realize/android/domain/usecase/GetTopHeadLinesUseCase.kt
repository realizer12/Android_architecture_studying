package com.realize.android.domain.usecase

import com.realize.android.domain.entity.ArticleDataEntity
import com.realize.android.domain.repository.TopNewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetTopHeadLinesUseCase @Inject constructor(
    private val topNewsRepository: TopNewsRepository
) {

    operator fun invoke(
        fromLocal: Boolean = false,
        category: String? = null,//optional
        page: Int? = null,
        pageSize: Int? = null
    ): Single<List<ArticleDataEntity>> {
        if(page == null || pageSize == null){
            throw Exception("page or page size null")
        }
        return if (fromLocal) {
            topNewsRepository.getSavedArticleList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        } else {
            topNewsRepository.getTopHeadLines(category, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

}