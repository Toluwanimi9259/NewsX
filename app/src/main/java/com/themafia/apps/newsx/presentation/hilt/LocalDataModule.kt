package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.data.repository.dataSource.LocalDataSource
import com.themafia.apps.newsx.data.repository.dataSourceImpl.LocalDataSourceImpl
import com.themafia.apps.newsx.data.room.dao.ArticleDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDataModule(articleDAO: ArticleDAO) : LocalDataSource{
        return LocalDataSourceImpl(articleDAO)
    }
}