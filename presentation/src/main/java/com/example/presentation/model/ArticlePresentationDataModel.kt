package com.example.presentation.model

import android.os.Parcelable
import com.example.presentation.mapper.ArticlePresentationMapper
import com.realize.android.domain.entity.ArticleDataEntity
import kotlinx.parcelize.Parcelize

/**
 * presentation 모듈용  article data model
**/
@Parcelize
data class ArticlePresentationDataModel(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
): Parcelable{
    companion object : ArticlePresentationMapper<ArticlePresentationDataModel, ArticleDataEntity>{
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
}
