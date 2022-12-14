package com.themafia.apps.newsx.data.room.dao

import androidx.room.*
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Update
    suspend fun updateArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

}