package com.example.remote.feature.news.impl

import com.example.data.model.ArticleDataModel
import com.example.data.model.BaseDataModel
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import com.example.remote.retrofit.RetrofitHelper
import com.example.util.const.Const
import io.reactivex.rxjava3.core.Single


//실세  topnewremote datasource의 구현체
class TopNewsRemoteDataSourceImpl(private val retrofitHelper: RetrofitHelper) :
    TopNewsRemoteDataSource {
    override fun getTopHeadLines(
        category: String?,
        page: Int,
        pageSize: Int
    ): Single<BaseDataModel<ArticleDataModel>> {
        return retrofitHelper.apiService.getTopHeadLines(
            page = page,
            category = category,
            pageSize = Const.PageSize
        ).onErrorReturn {
            throw Exception(it.message)
        }
    }

}