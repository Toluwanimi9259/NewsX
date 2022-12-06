package com.themafia.apps.newsx.data.repository

import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : Repository {

    private fun responseToResource(response : Response<APIResponse>) : Resource<APIResponse>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNewsHeadlines(country : String , page : Int): Resource<APIResponse> = responseToResource(remoteDataSource.getTopHeadlines(country, page))

    override suspend fun getSearchedNews(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

}