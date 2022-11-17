package com.example.data.source.remote.news

import com.example.data.model.Article
import com.example.data.model.BaseDataModel
import io.reactivex.rxjava3.core.Single


// 탑 뉴스 remote datasource interface 모음
interface TopNewsRemoteDataSource {
    fun getTopHeadLines(
        category: String? = null,//optional
        page: Int,
        pageSize: Int
    ): Single<BaseDataModel<Article>>
}