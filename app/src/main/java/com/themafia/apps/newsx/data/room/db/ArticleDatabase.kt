package com.themafia.apps.newsx.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.data.room.converter.Converters
import com.themafia.apps.newsx.data.room.dao.ArticleDAO

@Database(entities = [Article::class] , version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDAO
}