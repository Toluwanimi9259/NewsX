package com.themafia.apps.newsx.data.repository.dataSource

import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveArticleToDB(article: Article)

    fun getAllSavedArticles() : Flow<List<Article>>

    suspend fun deleteArticle(article: Article)
}