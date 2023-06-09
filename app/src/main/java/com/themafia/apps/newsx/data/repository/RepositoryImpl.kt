package com.themafia.apps.newsx.data.repository

import com.themafia.apps.newsx.data.repository.dataSource.LocalDataSource
import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    private fun responseToResource(response : Response<APIResponse>) : Resource<APIResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNewsHeadlines(country : String , page : Int): Resource<APIResponse> =
        responseToResource(remoteDataSource.getTopHeadlines(country, page))

    override suspend fun getSearchedNews(country : String , page : Int , searchQuery: String): Resource<APIResponse> =
        responseToResource(remoteDataSource.getSearchedTopHeadlines(country , page , searchQuery))

    override suspend fun getSearchedNews2(keyword: String): Resource<APIResponse> =
        responseToResource(remoteDataSource.getTopSearchedHeadlines2(keyword))

    override fun getSavedNews(): Flow<List<Article>> = localDataSource.getAllSavedArticles()

    override suspend fun deleteNews(article: Article) {
        localDataSource.deleteArticle(article)
    }

    override suspend fun saveNews(article: Article) {
        localDataSource.saveArticleToDB(article)
    }

}