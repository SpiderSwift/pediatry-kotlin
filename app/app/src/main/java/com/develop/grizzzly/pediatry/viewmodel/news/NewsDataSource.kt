package com.develop.grizzzly.pediatry.viewmodel.news

import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.develop.grizzzly.pediatry.network.WebAccess
import com.develop.grizzzly.pediatry.network.model.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class NewsDataSource : PositionalDataSource<News>() {

    private val apiService = WebAccess.pediatryApi

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<News>) {
        GlobalScope.launch {
            val response = apiService.getNews(params.requestedStartPosition.toLong() , params.requestedLoadSize.toLong())
            when{
                response.isSuccessful -> {
                    val listing = response.body()
                    Log.d("TAG", response.toString() + "response body : ${response.body().toString()}")
                    callback.onResult(listOf(News()),0)
                }
            }


        }

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<News>) {
        //todo another scope?
        GlobalScope.launch {
            val response = apiService.getNews(params.loadSize.toLong(), params.startPosition.toLong())
            when{
                response.isSuccessful -> {
                    val listing = response.body()
                    Log.d("TAG", response.toString() + "response body : ${response.body().toString()}")
                    callback.onResult(listing?.response ?: listOf())
                }
            }

        }
    }



}