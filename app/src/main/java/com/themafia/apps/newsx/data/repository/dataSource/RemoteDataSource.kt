package com.themafia.apps.newsx.data.repository.dataSource

import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getTopHeadlines(country : String , page : Int) : Response<APIResponse>
}