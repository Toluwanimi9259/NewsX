package com.themafia.apps.newsx.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase

class NewsViewModelFactory(
    private val app : Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlinesUseCase) as T
    }
}