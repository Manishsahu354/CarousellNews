package com.example.carousellnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carousellnews.data.model.NewsResponseModelItem
import com.example.carousellnews.databinding.ActivityMainBinding
import com.example.carousellnews.ui.NewsAdapter
import com.example.carousellnews.utils.Constants
import com.example.carousellnews.utils.NetworkResult
import com.example.carousellnews.utils.Utility
import com.example.carousellnews.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var newsList: MutableList<NewsResponseModelItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerview()
        setupViewModel()
    }

    private fun setupViewModel() {
        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.getNewsData()
        newsViewModel.newsData.observe(this) { response ->
            when (response) {
                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let { resultList ->
                        if (resultList.isNotEmpty()) {
                            newsList = resultList
                            newsAdapter.submitList(newsList)
                        }
                    }
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupRecyclerview() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sortedList: MutableList<NewsResponseModelItem> = mutableListOf()
        sortedList.addAll(newsList)
        when (item.itemId) {
            R.id.popular -> {
                Collections.sort(sortedList, Utility.popularSortComparator)
            }
            R.id.recent -> {
                sortedList.sortByDescending {
                    it.timeCreated
                }
            }
        }
        newsAdapter.submitList(sortedList) {
            binding.rvNews.scrollToPosition(0)
        }
        return super.onOptionsItemSelected(item)
    }
}