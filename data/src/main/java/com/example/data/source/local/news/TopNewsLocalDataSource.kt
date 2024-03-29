package com.example.data.source.local.news

import com.example.data.model.ArticleDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Date: 2022/10/30
 * Author: idonghun
 *
 * Content: 로컬 io 코드가 들어가는   로컬 datasource interface 이다.
 *
 * @see getSavedArticleList 로컬 룸에 저장한  게시글 리스트를 가져온다.
 * @see saveArticle 로컬 룸에 게시글을 저장한다.
 * @see removeArticle 로컬 룸에 게시글을 삭제한다.
 *
 * **/
interface TopNewsLocalDataSource {
    fun getSavedArticleList(): Single<List<ArticleDataModel>>
    fun saveArticle(article: ArticleDataModel): Completable
    fun removeArticle(article: ArticleDataModel): Completable
}