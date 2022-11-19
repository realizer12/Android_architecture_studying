package com.example.local.mapper

interface ArticleListLocalMapper <T,E> {
    fun T.toArticleListData():E
    fun E.fromArticleListData():T
}

interface ArticleLocalMapper <T,E> {
    fun T.toArticleData():E
    fun E.fromArticleData():T
}