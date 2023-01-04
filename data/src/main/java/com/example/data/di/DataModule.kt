package com.example.data.di

import com.realize.android.domain.repository.TopNewsRepository
import com.example.data.repository.news.TopNewsRepositoryImpl
import com.example.data.repository.news.UserInfoRepositoryImpl
import com.example.data.source.local.news.TopNewsLocalDataSource
import com.example.data.source.local.news.UserInfoLocalDataSource
import com.example.data.source.remote.news.TopNewsRemoteDataSource
import com.realize.android.domain.repository.UserInfoRepository
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

    //이거는 계속해서 바뀌어야 되므로, singletone을  안함.
    @Provides
    fun provideTopNewsRepository(
        savedNewsLocalDataSource: TopNewsLocalDataSource,
        topNewsRemoteDataSource: TopNewsRemoteDataSource
    ): TopNewsRepository = TopNewsRepositoryImpl(topNewsRemoteDataSource, savedNewsLocalDataSource)

    @Provides
    @Singleton
    fun provideUserInfoRepository(
        userInfoLocalDataSource: UserInfoLocalDataSource
    ):UserInfoRepository = UserInfoRepositoryImpl(userInfoLocalDataSource)

}