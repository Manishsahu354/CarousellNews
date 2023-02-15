package com.example.carousellnews.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponseModel(

	@field:SerializedName("NewsResponseModel")
	val newsResponseModel: MutableList<NewsResponseModelItem>? = null
)

data class NewsResponseModelItem(

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("time_created")
	val timeCreated: Long,

	@field:SerializedName("rank")
	val rank: Int,

	@field:SerializedName("banner_url")
	val bannerUrl: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("title")
	val title: String
)
