package com.themafia.apps.newsx.data.room.dao

import androidx.room.*
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Update
    suspend fun updateArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllSavedArticles() : Flow<List<Article>>

}