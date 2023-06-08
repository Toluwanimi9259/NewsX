package com.themafia.apps.newsx.presentation.hilt

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.themafia.apps.newsx.data.room.dao.ArticleDAO
import com.themafia.apps.newsx.data.room.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideArticleDatabase(app : Application) : ArticleDatabase {
        return Room.databaseBuilder(app , ArticleDatabase::class.java , "news_database").
        fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(articleDatabase: ArticleDatabase) : ArticleDAO{
     return articleDatabase.articleDao()
    }
}