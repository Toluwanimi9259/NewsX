package com.themafia.apps.newsx.domain.repository

import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Remote
    suspend fun getNewsHeadlines() : Resource<APIResponse>

    suspend fun getSearchedNews(searchQuery : String) : Resource<APIResponse>

    //Local
    fun getSavedNews() : Flow<List<Article>>

    suspend fun deleteNews(article: Article)

    suspend fun saveNews(article: Article)

}