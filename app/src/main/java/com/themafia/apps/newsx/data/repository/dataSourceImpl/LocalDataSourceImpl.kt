package com.themafia.apps.newsx.data.repository.dataSourceImpl

import com.themafia.apps.newsx.data.repository.dataSource.LocalDataSource
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.room.dao.ArticleDAO

class LocalDataSourceImpl(private val articleDAO: ArticleDAO) : LocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDAO.insertArticle(article)
    }
}