package com.themafia.apps.newsx.presentation.hilt

import com.themafia.apps.newsx.presentation.adapters.Adapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapter() : Adapter{
        return Adapter("news")
    }

}