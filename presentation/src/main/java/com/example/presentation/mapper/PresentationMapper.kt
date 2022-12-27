package com.example.presentation.mapper


/**
 * presentation 모듈  mapping interface
 **/
interface ArticlePresentationMapper<T, E> {
    fun T.toArticleData(): E
    fun E.fromArticleData(): T
}

/**
 * 이걸로 다 바꿔야됨.
**/
interface ArticlePresentationMapper1<T, E> {
    fun T.toArticleEntity(): E
    fun E.fromArticleEntity(): T
}
