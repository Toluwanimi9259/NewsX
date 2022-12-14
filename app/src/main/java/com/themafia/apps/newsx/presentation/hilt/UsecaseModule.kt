package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.domain.repository.Repository
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase
import com.themafia.apps.newsx.domain.usecases.GetSearchedNews2UseCase
import com.themafia.apps.newsx.domain.usecases.GetSearchedNewsUseCase
import com.themafia.apps.newsx.domain.usecases.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UsecaseModule {

    @Singleton
    @Provides
    fun provideNewsHeadlinesModule(repository: Repository) : GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSearchedNewsHeadlinesModule(repository: Repository) : GetSearchedNewsUseCase {
        return GetSearchedNewsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSearchedNews2Headlines(repository: Repository) : GetSearchedNews2UseCase{
        return GetSearchedNews2UseCase(repository)

    }
    @Singleton
    @Provides
    fun provideSaveNewsModule(repository: Repository) : SaveNewsUseCase{
        return SaveNewsUseCase(repository)
    }
}