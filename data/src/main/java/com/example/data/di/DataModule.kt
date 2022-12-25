package com.example.data.di

import com.example.data.repository.news.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.data.source.local.news.SavedNewsLocalDataSource
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * data module Repository hilt 의존성 적용
 **/
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTopNewsRepository(
        savedNewsLocalDataSource: SavedNewsLocalDataSource,
        topNewsRemoteDataSource: TopNewsRemoteDataSource
    ): TopNewsRepository = TopNewsRepositoryImpl(topNewsRemoteDataSource, savedNewsLocalDataSource)

}