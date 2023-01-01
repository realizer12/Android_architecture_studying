package com.example.local.di

import com.example.data.source.local.news.TopNewsLocalDataSource
import com.example.local.feature.news.impl.TopNewsLocalDataSourceImpl
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
    @Singleton
    fun provideSavedNewsLocalDataSource(
        newsArticleDao: NewsArticleDao
    ):TopNewsLocalDataSource = TopNewsLocalDataSourceImpl(newsArticleDao)


}