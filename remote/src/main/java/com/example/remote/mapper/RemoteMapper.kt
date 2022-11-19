package com.example.remote.mapper

/**
 * remote 모듈용  mapper 이다.
 * remote 모듈에서 나온 data를  data 모듈용으로 바꾸어서  return 한다.
**/
interface ArticleRemoteMapper <T,E> {
    fun T.toArticleData():E
    fun E.fromArticleData():T
}

interface BaseModelRemoteMapper <T,E> {
    fun T.toBaseModelData():E
    fun E.fromBaseModelData():T
}