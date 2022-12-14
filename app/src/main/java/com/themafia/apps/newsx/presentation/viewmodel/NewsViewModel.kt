package com.themafia.apps.newsx.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.*
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.usecases.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app : Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val getSearchedNews2UseCase: GetSearchedNews2UseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
    ) : AndroidViewModel(app) {

    val newsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country : String, page : Int) = viewModelScope.launch {
        newsHeadlines.postValue( Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val newsResult = getNewsHeadlinesUseCase.execute(country , page)
                newsHeadlines.postValue(newsResult)
            }else {
                newsHeadlines.postValue(Resource.Error("Internet Connection Problem"))
                Log.d("MYTAG" , "Internet Connection Problem")
            }
        }catch (e : Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
            Log.d("MYTAG" , "Error : " + e.message.toString())
        }

    }

    // Search
    // This method works only for this country ie searches headlines in this country

    private val searchedNewsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNewsHeadlines(country : String , page : Int , keyword : String) = viewModelScope.launch {
        searchedNewsHeadlines.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val searchedNewsResult = getSearchedNewsUseCase.execute(country,page,keyword)
                searchedNewsHeadlines.postValue(searchedNewsResult)
            }else{
                searchedNewsHeadlines.postValue(Resource.Error("Internet Connection Problem"))
            }
        }catch (e : Exception){
            searchedNewsHeadlines.postValue(Resource.Error(e.message.toString()))
        }
    }

    // O.G Search

    val searchedNews2Headlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getSearchedNews2Headlines(keyword : String) = viewModelScope.launch {
        searchedNews2Headlines.postValue(Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val searchedNews2Result = getSearchedNews2UseCase.execute(keyword)
                searchedNews2Headlines.postValue(searchedNews2Result)
            }else{
                searchedNews2Headlines.postValue(Resource.Error("Internet Connection Problem"))
            }
        }catch (ex : Exception){
            searchedNews2Headlines.postValue(Resource.Error(ex.message.toString()))
        }
    }

    // Save News
    fun saveNewsToDB(article: Article) = viewModelScope.launch {
        saveNewsUseCase.execute(article)
    }

    // Get Saved News
    fun getSavedArticles() = liveData {
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }
}