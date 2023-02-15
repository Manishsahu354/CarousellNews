package com.example.carousellnews.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.carousellnews.data.model.NewsResponseModelItem
import com.example.carousellnews.repository.NewsRepository
import com.example.carousellnews.utils.Constants
import com.example.carousellnews.utils.NetworkResult
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    val _newsData: MutableLiveData<NetworkResult<MutableList<NewsResponseModelItem>>?> = MutableLiveData()
    val newsData: LiveData<NetworkResult<MutableList<NewsResponseModelItem>>?> = _newsData

    fun getNewsData(){

        viewModelScope.launch {
            _newsData.postValue(NetworkResult.Loading())
            val response =newsRepository.getNews()
            when (response.isSuccessful) {
                true -> {
                    _newsData.postValue(NetworkResult.Success(response.body()))
                }
                else -> {
                    _newsData.postValue(NetworkResult.Error("Something went wrong"))
                }
            }
        }
    }
}