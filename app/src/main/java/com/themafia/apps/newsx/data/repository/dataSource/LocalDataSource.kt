package com.themafia.apps.newsx.data.repository.dataSource

import com.themafia.apps.newsx.data.retrofit.dataclasses.Article

interface LocalDataSource {
    suspend fun saveArticleToDB(article: Article)
}