package com.example.latestnews.models



data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)