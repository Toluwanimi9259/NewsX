package com.themafia.apps.newsx.data.retrofit.api

import com.themafia.apps.newsx.BuildConfig
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country : String,
        @Query("page")
        page : Int,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_KEY
    ) : Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country")  // This method works only for this country ie searches headlines in this country
        country : String,
        @Query("q")
        searchKeyword: String,
        @Query("page")
        page : Int,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_KEY
    ) : Response<APIResponse>

    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines2(
        @Query("q")
        searchKeyword: String,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_KEY
    ) : Response<APIResponse>


}