package com.example.presentation.mapper


/**
 * presentation 모듈  mapping interface
 **/
interface ArticlePresentationMapper<T, E> {
    fun T.toArticleEntity(): E
    fun E.fromArticleEntity(): T
}
