package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.domain.repository.Repository
import com.themafia.apps.newsx.domain.usecases.GetNewsHeadlinesUseCase
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
}