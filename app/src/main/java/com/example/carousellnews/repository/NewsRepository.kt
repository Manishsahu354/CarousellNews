package com.example.carousellnews.repository

import com.example.carousellnews.data.network.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getNews() = newsApi.getNews()
}