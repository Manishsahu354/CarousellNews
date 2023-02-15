package com.example.carousellnews.data.network

import com.example.carousellnews.data.model.NewsResponseModelItem
import com.example.carousellnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {

    @GET(Constants.NEWS_URL)
    suspend fun getNews():Response<MutableList<NewsResponseModelItem>>

}