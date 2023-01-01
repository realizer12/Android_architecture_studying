package com.example.data.repository.news

import com.example.data.mapper.ArticleListDataMapper
import com.example.data.model.ArticleDataModel
import com.example.data.source.local.news.TopNewsLocalDataSource
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import com.realize.android.domain.entity.ArticleDataEntity
import com.realize.android.domain.repository.TopNewsRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TopNewsRepositoryImpl @Inject constructor(
    private val topNewsRemoteDataSource: TopNewsRemoteDataSource,
    private val savedNewsLocalDataSource: TopNewsLocalDataSource
) : TopNewsRepository, ArticleListDataMapper<ArticleDataModel, ArticleDataEntity> {
    override fun getTopHeadLines(
        category: String?,
        page: Int,
        pageSize: Int
    ): Single<List<ArticleDataEntity>> {
        return topNewsRemoteDataSource.getTopHeadLines(category, page, pageSize).map {
            if (it.status == "ok") {
                it.articles?.map {
                    it.toArticleListEntity()
                } ?: emptyList()
            } else {
                throw Exception(it.message)
            }
        }
    }

    override fun getSavedArticleList(): Single<List<ArticleDataEntity>> {
        return savedNewsLocalDataSource.getSavedArticleList().map {
            it.map { it.toArticleListEntity() }
        }
    }

    override fun saveArticle(article: ArticleDataEntity): Completable {
        return savedNewsLocalDataSource.saveArticle(article.fromArticleListEntity())
    }

    override fun removeArticle(article: ArticleDataEntity): Completable {
        return savedNewsLocalDataSource.removeArticle(article.fromArticleListEntity())
    }

    override fun ArticleDataModel.toArticleListEntity(): ArticleDataEntity {
        return ArticleDataEntity(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

    override fun ArticleDataEntity.fromArticleListEntity(): ArticleDataModel {
        return ArticleDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

}