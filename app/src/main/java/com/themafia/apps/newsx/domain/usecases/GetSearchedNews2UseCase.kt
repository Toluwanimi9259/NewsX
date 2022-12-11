package com.themafia.apps.newsx.domain.usecases

import com.themafia.apps.newsx.data.retrofit.dataclasses.APIResponse
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.domain.repository.Repository

class GetSearchedNews2UseCase(private val repo : Repository) {
    suspend fun execute(keyword : String) : Resource<APIResponse> = repo.getSearchedNews2(keyword)
}