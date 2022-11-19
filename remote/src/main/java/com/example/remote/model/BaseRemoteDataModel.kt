package com.example.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * remote 모듈용 base  data model 이다.
**/
@Parcelize
data class BaseRemoteDataModel<T>(
    val status: String,
    val totalResults: Int = 0,
    val articles: @RawValue List<T>? = null,
    val message:String?=null//에러 메세지
): Parcelable

