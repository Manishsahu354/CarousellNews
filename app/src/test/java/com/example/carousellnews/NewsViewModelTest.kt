package com.example.carousellnews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.carousellnews.data.model.NewsResponseModelItem
import com.example.carousellnews.data.network.NewsApi
import com.example.carousellnews.repository.NewsRepository
import com.example.carousellnews.utils.NetworkResult
import com.example.carousellnews.viewmodels.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class NewsViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var newsViewModel: NewsViewModel
    lateinit var newsRepository: NewsRepository

    @Mock
    lateinit var apiService: NewsApi

    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        newsRepository = NewsRepository(apiService)
        newsViewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun testGetNews_expectedNewsList() {
        runBlocking {
            Mockito.`when`(newsRepository.getNews())
                .thenReturn(Response.success(mutableListOf(NewsResponseModelItem("news", 48488484884, 1,"https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg","1","title"))))
            newsViewModel.getNewsData()
            val result = newsViewModel.newsData.value
            assertEquals(true,result is NetworkResult.Success)
            assertEquals(1,result?.data?.size)
            assertEquals("news", result?.data!![0].description)
            assertEquals(48488484884, result.data!![0].timeCreated)
            assertEquals(1, result.data!![0].rank)
            assertEquals("https://storage.googleapis.com/carousell-interview-assets/android/images/carousell-siu-rui-ceo-tia-sg-2018.jpg", result.data!![0].bannerUrl)
            assertEquals("1", result.data!![0].id)
            assertEquals("title", result.data!![0].title)
        }
    }


    @Test
    fun testGetNews_expectEmptyList() = runTest {
        Mockito.`when`(newsRepository.getNews())
            .thenReturn(Response.success(mutableListOf()))
        newsViewModel.getNewsData()
        val result = newsViewModel.newsData.value
        assertEquals(true,result is NetworkResult.Success)
        assertEquals(0,result?.data?.size)
    }

    @Test
    fun testGetNews_expectedError() = runTest {
        Mockito.`when`(newsRepository.getNews())
            .thenReturn(Response.error(401,"Unathorized".toResponseBody()))
        newsViewModel.getNewsData()
        val result = newsViewModel.newsData.value
        assertEquals(true,result is NetworkResult.Error)
        assertEquals("Something went wrong",result?.message)
    }

}