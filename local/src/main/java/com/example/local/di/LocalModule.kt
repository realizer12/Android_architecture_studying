package com.example.local.di

import com.example.data.source.local.news.SavedNewsLocalDataSource
import com.example.local.feature.news.impl.SavedNewsLocalDataSourceImpl
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
    ):SavedNewsLocalDataSource = SavedNewsLocalDataSourceImpl(newsArticleDao)


}