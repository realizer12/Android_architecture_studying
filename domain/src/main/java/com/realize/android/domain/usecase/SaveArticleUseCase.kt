package com.realize.android.domain.usecase

import com.realize.android.domain.entity.ArticleDataEntity
import com.realize.android.domain.repository.TopNewsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SaveArticleUseCase @Inject constructor(
    private val topNewsRepository: TopNewsRepository
) {

    operator fun invoke(
        article: ArticleDataEntity?=null
    ): Completable {
        if(article == null){
            throw Exception("null exception")
        }
        return topNewsRepository.saveArticle(article)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}