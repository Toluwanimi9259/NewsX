package com.themafia.apps.newsx.domain.usecases

import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.domain.repository.Repository

class DeleteSavedNewsUseCase(private val repository: Repository) {
    suspend fun execute(article: Article) = repository.deleteNews(article)
}