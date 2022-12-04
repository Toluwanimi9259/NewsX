package com.themafia.apps.newsx.domain.usecases

import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val repository: Repository) {
    fun execute() : Flow<List<Article>> = repository.getSavedNews()
}