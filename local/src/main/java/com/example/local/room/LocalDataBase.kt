package com.example.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.model.ArticleLocalDataModel

@Database(
    entities = [ArticleLocalDataModel::class],
    version = 5,
    exportSchema = false
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getNewsArticleDao(): NewsArticleDao
}