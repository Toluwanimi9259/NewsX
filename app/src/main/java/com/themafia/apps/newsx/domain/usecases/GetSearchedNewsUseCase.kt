package com.themafia.apps.newsx.domain.usecases

import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.repository.Repository

class GetSearchedNewsUseCase(private val repository: Repository) {
    suspend fun execute(searchQuery : String) : Resource<APIResponse> = repository.getSearchedNews(searchQuery)
}