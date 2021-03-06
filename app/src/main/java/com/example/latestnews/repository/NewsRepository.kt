package com.example.latestnews.repository

import com.example.latestnews.api.RetrofitInstance
import com.example.latestnews.db.ArticleDatabase
import com.example.latestnews.models.Article


class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String,category:String,pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode,category, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun getSavedArticles() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}