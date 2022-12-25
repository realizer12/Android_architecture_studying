package com.example.remote.di

import com.example.data.source.remote.news.TopNewsRemoteDataSource
import com.example.remote.feature.news.impl.TopNewsRemoteDataSourceImpl
import com.example.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * datasource hilt 의존성 주입용
 **/
@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    //top news 전용  Remote 데이터 소스를 제공한다.
    @Provides
    fun provideTopNewsRemoteDataSource(
        apiService: ApiService
    ): TopNewsRemoteDataSource = TopNewsRemoteDataSourceImpl(apiService = apiService)
}