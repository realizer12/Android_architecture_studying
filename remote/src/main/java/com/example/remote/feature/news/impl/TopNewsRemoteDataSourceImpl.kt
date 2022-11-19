package com.example.remote.feature.news.impl

import com.example.data.model.ArticleDataModel
import com.example.data.model.BaseDataModel
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import com.example.remote.mapper.ArticleRemoteMapper
import com.example.remote.mapper.BaseModelRemoteMapper
import com.example.remote.model.ArticleRemoteDataModel
import com.example.remote.model.BaseRemoteDataModel
import com.example.remote.retrofit.RetrofitHelper
import com.example.util.const.Const
import io.reactivex.rxjava3.core.Single


//실세  topnewremote datasource의 구현체
class TopNewsRemoteDataSourceImpl(private val retrofitHelper: RetrofitHelper) :
    TopNewsRemoteDataSource,
    BaseModelRemoteMapper<BaseRemoteDataModel<ArticleRemoteDataModel>, BaseDataModel<ArticleDataModel>>,
    ArticleRemoteMapper<ArticleRemoteDataModel, ArticleDataModel> {

    override fun getTopHeadLines(
        category: String?,
        page: Int,
        pageSize: Int
    ): Single<BaseDataModel<ArticleDataModel>> {
        return retrofitHelper.apiService.getTopHeadLines(
            page = page,
            category = category,
            pageSize = Const.PageSize
        ).map {
            it.toBaseModelData()
        }.onErrorReturn {
            throw Exception(it.message)
        }
    }


    override fun ArticleRemoteDataModel.toArticleData(): ArticleDataModel {
        return ArticleDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

    override fun ArticleDataModel.fromArticleData(): ArticleRemoteDataModel {
        return ArticleRemoteDataModel(
            author, content, description, publishedAt, title, url, urlToImage
        )
    }

    override fun BaseRemoteDataModel<ArticleRemoteDataModel>.toBaseModelData(): BaseDataModel<ArticleDataModel> {
        return BaseDataModel(
            status = status,
            totalResults = totalResults,
            articles = articles?.map {
                it.toArticleData()
            } ?: emptyList(),
            message = message
        )
    }

    override fun BaseDataModel<ArticleDataModel>.fromBaseModelData(): BaseRemoteDataModel<ArticleRemoteDataModel> {
        return BaseRemoteDataModel(
            status = status,
            totalResults = totalResults,
            articles = articles?.map {
                it.fromArticleData()
            } ?: emptyList(),
            message = message
        )
    }

}