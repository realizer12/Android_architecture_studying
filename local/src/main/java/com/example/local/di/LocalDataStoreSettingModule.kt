package com.example.local.di

import android.content.Context
import android.content.SharedPreferences
import com.example.local.room.LocalDataBase
import com.example.local.room.NewsArticleDao
import com.example.local.util.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * 로컬 데이터 사용을 위한 room 세팅 코드 hilt 적용
**/
@Module
@InstallIn(SingletonComponent::class)
object LocalDataStoreSettingModule {

    //news artricle dao 의존성 주입
    @Provides
    @Singleton
    fun provideNewsArticleDao(
        @ApplicationContext context: Context
    ) :NewsArticleDao = LocalDataBase.getInstance(context = context).getNewsArticleDao()

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ):SharedPreferences = context.getSharedPreferences(
        Const.GLOBAL_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

}