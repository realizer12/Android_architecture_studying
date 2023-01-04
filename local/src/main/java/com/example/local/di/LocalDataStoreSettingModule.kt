package com.example.local.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.local.room.LocalDataBase
import com.example.local.room.NewsArticleDao
import com.example.local.util.Const.GLOBAL_PREFERENCE_NAME
import com.example.local.util.Const.PARAM_IS_LOGIN_ID
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

    @Provides
    @Singleton
    fun provideRoomDataBase(
        @ApplicationContext context: Context,
        preferences: SharedPreferences
    ): LocalDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDataBase::class.java,
            "${preferences.getString(PARAM_IS_LOGIN_ID, "")
            }_local-database.db"
        ).fallbackToDestructiveMigration()
            .build()
    }


    //news artricle dao 의존성 주입
    @Provides
    @Singleton
    fun provideNewsArticleDao(
        localDataBase: LocalDataBase
    ) :NewsArticleDao = localDataBase.getNewsArticleDao()

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ):SharedPreferences = context.getSharedPreferences(
        GLOBAL_PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

}