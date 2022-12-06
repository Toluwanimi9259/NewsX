package com.themafia.apps.newsx.data.repository.dataSourceImpl

import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.data.retrofit.api.NewsService
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import retrofit2.Response

class RemoteDataSourceImpl(private val newsService: NewsService,
                           ) : RemoteDataSource {

    override suspend fun getTopHeadlines(country : String , page : Int): Response<APIResponse> = newsService.getTopHeadlines(country, page)

}