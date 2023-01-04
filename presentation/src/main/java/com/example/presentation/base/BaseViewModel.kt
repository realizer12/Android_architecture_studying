package com.example.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.mapper.ArticlePresentationMapper1
import com.example.presentation.model.ArticlePresentationDataModel
import com.example.presentation.util.Event
import com.realize.android.domain.entity.ArticleDataEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo

/**
 * 베이스가 되는 뷰모델이다. 
**/
abstract class BaseViewModel:ViewModel(),
    ArticlePresentationMapper1<ArticlePresentationDataModel,ArticleDataEntity>
{
    private val compositeDisposable = CompositeDisposable()

    //error toast는 공통 사용이므로, base viewmodel 에 넣어줌.
    protected val _errorToast = MutableLiveData<Event<Throwable>>()
    val errorToast: LiveData<Event<Throwable>> = _errorToast

    fun Disposable.addDisposable(){
        this.addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }


    override fun ArticlePresentationDataModel.toArticleEntity(): ArticleDataEntity {
        return  ArticleDataEntity(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

    override fun ArticleDataEntity.fromArticleEntity(): ArticlePresentationDataModel {
        return ArticlePresentationDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }
}