package com.realize.android.domain.usecase

import com.realize.android.domain.entity.ArticleDataEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CheckSavedArticleUseCase @Inject constructor(
    private val getTopHeadLinesUseCase: GetTopHeadLinesUseCase
) {
    operator fun invoke(
        articleDataEntity: ArticleDataEntity?
    ): Single<Boolean> {

        if(articleDataEntity == null){
            throw Exception("detail article null")
        }

        return getTopHeadLinesUseCase(fromLocal = true)
            .observeOn(Schedulers.io())
            .map { saveArticleList ->
                saveArticleList.any { it.title == articleDataEntity.title && it.publishedAt == articleDataEntity.publishedAt && it.url == articleDataEntity.url }
            }.observeOn(AndroidSchedulers.mainThread())
    }
}