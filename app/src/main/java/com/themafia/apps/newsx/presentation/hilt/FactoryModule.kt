package com.themafia.apps.newsx.presentation.hilt

import android.app.Application
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase
import com.themafia.apps.newsx.domain.usecases.GetSearchedNewsUseCase
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(app : Application , getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase , getSearchedNewsUseCase: GetSearchedNewsUseCase) : NewsViewModelFactory{
        return NewsViewModelFactory(app, getNewsHeadlinesUseCase , getSearchedNewsUseCase)
    }

}