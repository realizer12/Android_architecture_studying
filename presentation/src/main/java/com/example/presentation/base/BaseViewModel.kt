package com.example.presentation.base

import androidx.lifecycle.ViewModel
import com.example.data.model.ArticleDataModel
import com.example.presentation.mapper.ArticlePresentationMapper
import com.example.presentation.model.ArticlePresentationDataModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo

/**
 * 베이스가 되는 뷰모델이다. 
**/
open class BaseViewModel:ViewModel(),
    ArticlePresentationMapper<ArticlePresentationDataModel, ArticleDataModel> {
    private val compositeDisposable = CompositeDisposable()

    fun Disposable.addDisposable(){
        this.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }

    override fun ArticleDataModel.fromArticleData(): ArticlePresentationDataModel {
        return  ArticlePresentationDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

    override fun ArticlePresentationDataModel.toArticleData(): ArticleDataModel {
        return ArticleDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }
}