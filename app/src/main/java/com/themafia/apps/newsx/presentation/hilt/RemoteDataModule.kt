package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.data.repository.dataSourceImpl.RemoteDataSourceImpl
import com.themafia.apps.newsx.data.retrofit.api.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(newsService: NewsService) : RemoteDataSource{
        return RemoteDataSourceImpl(newsService)
    }
}