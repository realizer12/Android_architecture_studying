package com.example.presentation.repository

import com.example.presentation.model.Article
import com.example.presentation.model.BaseDataModel
import retrofit2.Call
import retrofit2.Callback

/**
 * Date: 2022/10/30
 * Author: idonghun
 *
 * Content:  top new를 가져오는 레포지토리
 *
 * @see  getTopHeadLines  top news api에셔  paramter 값에 해당하는  게시글 리스트를 받아온다.
 * @see  getSavedArticleList 로컬 db 에  저장한  게시글 리스트를 받아온다.
 * **/
interface TopNewsRepository {
    fun getTopHeadLines(
        category: String? = null,//optional
        page:Int,
        pageSize:Int
    ): Call<BaseDataModel<Article>>

    fun getSavedArticleList(callback:(List<Article>?, Throwable?)->Unit)
}