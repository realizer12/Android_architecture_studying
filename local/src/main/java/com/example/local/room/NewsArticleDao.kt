package com.example.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.model.ArticleDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface NewsArticleDao {
    @Insert(onConflict = REPLACE)
    fun setSavedArticle(article: ArticleDataModel): Completable

    @Query("SELECT * FROM newsArticleTable")
    fun loadSavedNewsArticles(): Single<List<ArticleDataModel>>

    @Query("DELETE FROM newsArticleTable WHERE publishedAt = :publishedAt AND title = :title AND url = :url")
    fun deleteSavedArticle(publishedAt: String, title: String, url: String): Completable
}