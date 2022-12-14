package com.themafia.apps.newsx.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase
import com.themafia.apps.newsx.domain.usecases.GetSearchedNews2UseCase
import com.themafia.apps.newsx.domain.usecases.GetSearchedNewsUseCase
import com.themafia.apps.newsx.domain.usecases.SaveNewsUseCase

class NewsViewModelFactory(
    private val app : Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val getSearchedNews2UseCase: GetSearchedNews2UseCase,
    private val saveNewsUseCase: SaveNewsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlinesUseCase,getSearchedNewsUseCase , getSearchedNews2UseCase , saveNewsUseCase) as T
    }
}