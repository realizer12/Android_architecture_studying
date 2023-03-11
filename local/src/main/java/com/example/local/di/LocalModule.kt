package com.example.local.di

import android.content.SharedPreferences
import com.example.data.source.local.news.TopNewsLocalDataSource
import com.example.data.source.local.news.UserInfoLocalDataSource
import com.example.local.feature.news.impl.TopNewsLocalDataSourceImpl
import com.example.local.feature.news.impl.UserInfoLocalDataSourceImpl
import com.example.local.room.NewsArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * local datasource전용 hilt 적용 class
**/
@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideSavedNewsLocalDataSource(
        newsArticleDao: NewsArticleDao
    ):TopNewsLocalDataSource = TopNewsLocalDataSourceImpl(newsArticleDao)

    @Provides
    @Singleton
    fun provideUserInfoLocalDataSource(
        preferences: SharedPreferences
    ):UserInfoLocalDataSource = UserInfoLocalDataSourceImpl(preferences)

}