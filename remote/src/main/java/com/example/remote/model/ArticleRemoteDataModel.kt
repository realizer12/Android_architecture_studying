package com.example.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * remote 모듈용 article model
 **/
@Parcelize
data class ArticleRemoteDataModel(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
) : Parcelable
