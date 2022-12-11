package com.themafia.apps.newsx.data.repository.dataSource

import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getTopHeadlines(country : String , page : Int) : Response<APIResponse>

    suspend fun getSearchedTopHeadlines(country : String , page : Int , keyword : String) : Response<APIResponse>

    suspend fun getTopSearchedHeadlines2(keyword : String) : Response<APIResponse>
}