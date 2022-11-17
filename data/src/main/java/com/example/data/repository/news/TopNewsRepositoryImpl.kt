package com.example.data.repository.news

import com.example.data.model.Article
import com.example.data.source.local.news.SavedNewsLocalDataSource
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class TopNewsRepositoryImpl(
    private val topNewsRemoteDataSource: TopNewsRemoteDataSource,
    private val savedNewsLocalDataSource: SavedNewsLocalDataSource
) :
    TopNewsRepository {
    override fun getTopHeadLines(
        category: String?,
        page: Int,
        pageSize: Int
    ): Single<List<Article>> {
        return topNewsRemoteDataSource.getTopHeadLines(category, page, pageSize).map {
            if (it.status == "ok") {
                it.articles ?: emptyList()
            } else {
                throw Exception(it.message)
            }
        }
    }

    override fun getSavedArticleList(): Single<List<Article>> {
        return savedNewsLocalDataSource.getSavedArticleList()
    }

    override fun saveArticle(article: Article): Completable {
        return savedNewsLocalDataSource.saveArticle(article)
    }

    override fun removeArticle(article: Article): Completable {
        return savedNewsLocalDataSource.removeArticle(article)
    }
}