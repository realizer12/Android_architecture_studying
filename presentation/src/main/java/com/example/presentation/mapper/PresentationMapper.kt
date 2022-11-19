package com.example.presentation.mapper


/**
 * presentation 모듈  mapping interface
**/
interface PresentationMapper<T, E> {
    fun T.toData(): E
}