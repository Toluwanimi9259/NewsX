package com.themafia.apps.newsx.data.repository.dataSourceImpl

import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.data.retrofit.api.NewsService
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import retrofit2.Response

class RemoteDataSourceImpl(private val newsService: NewsService,
                           private val country : String ,
                           private val page : Int) : RemoteDataSource {

    override suspend fun getTopHeadlines(): Response<APIResponse> = newsService.getTopHeadlines(country, page)

}