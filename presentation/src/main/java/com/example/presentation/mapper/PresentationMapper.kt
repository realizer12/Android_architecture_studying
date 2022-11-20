package com.example.presentation.mapper


/**
 * presentation 모듈  mapping interface
 **/
interface ArticlePresentationMapper<T, E> {
    fun T.toArticleData(): E
    fun E.fromArticleData(): T
}

