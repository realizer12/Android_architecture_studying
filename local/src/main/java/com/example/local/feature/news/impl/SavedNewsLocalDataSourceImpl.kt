package com.example.local.feature.news.impl

import com.example.data.model.ArticleDataModel
import com.example.data.source.local.news.SavedNewsLocalDataSource
import com.example.local.mapper.ArticleListLocalMapper
import com.example.local.mapper.ArticleLocalMapper
import com.example.local.model.ArticleLocalDataModel
import com.example.local.room.LocalDataBase
import com.example.local.room.NewsArticleDao
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Date: 2022/10/30
 * Author: idonghun
 *
 * Content: 저장한 게시글 가져오는  datasource의  실제  구현체 부분
 *
 * **/
class SavedNewsLocalDataSourceImpl @Inject constructor(
    private val newsArticleDao: NewsArticleDao
) : SavedNewsLocalDataSource,
    ArticleListLocalMapper<List<ArticleLocalDataModel>, List<ArticleDataModel>>,
    ArticleLocalMapper<ArticleLocalDataModel, ArticleDataModel> {

    override fun getSavedArticleList(): Single<List<ArticleDataModel>> {
        return newsArticleDao.loadSavedNewsArticles().map {
            it.toArticleListData()
        }
    }

    override fun saveArticle(article: ArticleDataModel): Completable {
        return newsArticleDao.setSavedArticle(article.fromArticleData())
    }

    override fun removeArticle(article: ArticleDataModel): Completable {
        return newsArticleDao.deleteSavedArticle(
            publishedAt = article.fromArticleData().publishedAt.toString(),
            title = article.fromArticleData().title.toString(),
            url = article.fromArticleData().url.toString()
        )
    }


    override fun ArticleLocalDataModel.toArticleData(): ArticleDataModel {
        return ArticleDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }


    override fun List<ArticleDataModel>.fromArticleListData(): List<ArticleLocalDataModel> {
        return this.map {
            ArticleLocalDataModel(
                author = it.author,
                content = it.content,
                description = it.description,
                publishedAt = it.publishedAt,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage
            )
        }
    }

    override fun ArticleDataModel.fromArticleData(): ArticleLocalDataModel {
        return ArticleLocalDataModel(
            author = this.author,
            content = this.content,
            description = this.description,
            publishedAt = this.publishedAt,
            title = this.title,
            url = this.url,
            urlToImage = this.urlToImage
        )
    }

    override fun List<ArticleLocalDataModel>.toArticleListData(): List<ArticleDataModel> {
        return this.map {
            ArticleDataModel(
                author = it.author,
                content = it.content,
                description = it.description,
                publishedAt = it.publishedAt,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage
            )
        }
    }
}
