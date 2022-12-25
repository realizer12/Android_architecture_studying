package com.example.local.di

import android.content.Context
import com.example.local.room.LocalDataBase
import com.example.local.room.NewsArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * 로컬 데이터 사용을 위한 room 세팅 코드 hilt 적용
**/
@Module
@InstallIn(SingletonComponent::class)
object LocalDataStoreSettingModule {

    //news artricle dao 의존성 주입
    @Provides
    fun provideNewsArticleDao(
        @ApplicationContext context: Context
    ) :NewsArticleDao = LocalDataBase.getInstance(context = context).getNewsArticleDao()

}