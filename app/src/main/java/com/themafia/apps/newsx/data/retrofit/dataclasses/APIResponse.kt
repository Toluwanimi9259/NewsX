package com.themafia.apps.newsx.data.retrofit.dataclasses


import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)