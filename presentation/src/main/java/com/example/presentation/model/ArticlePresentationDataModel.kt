package com.example.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * presentation 모듈용  article data model
**/
@Parcelize
data class ArticlePresentationDataModel(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
): Parcelable
