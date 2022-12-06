package com.themafia.apps.newsx.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app : Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
    ) : AndroidViewModel(app) {
    private val newsHeadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(country : String, page : Int) = viewModelScope.launch {
        newsHeadlines.postValue( Resource.Loading())

        try {
            if (isNetworkAvailable(app)){
                val newsResult = getNewsHeadlinesUseCase.execute(country , page)
                newsHeadlines.postValue(newsResult)
            }else {
                newsHeadlines.postValue(Resource.Error("Internet Connection Problem"))
            }
        }catch (e : Exception){
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
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