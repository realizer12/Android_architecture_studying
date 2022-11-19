package com.example.data.mapper

/* 구현을 dataClass에 하는 것과 impl에 하는 것 장단이 존재 */
interface ArticleListDataMapper <T,E> {
    fun T.toArticleListPresentation():E
    fun E.fromArticleListPresentation():T
}
