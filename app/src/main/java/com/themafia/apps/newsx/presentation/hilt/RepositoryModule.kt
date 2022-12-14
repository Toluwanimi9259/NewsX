package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.data.repository.RepositoryImpl
import com.themafia.apps.newsx.data.repository.dataSource.LocalDataSource
import com.themafia.apps.newsx.data.repository.dataSource.RemoteDataSource
import com.themafia.apps.newsx.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepositoryModule(remoteDataSource: RemoteDataSource , localDataSource: LocalDataSource) : Repository{
        return RepositoryImpl(remoteDataSource , localDataSource)
    }
}