package com.example.myfilimoapp.repository

import com.example.myfilimoapp.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api:ApiServices){
    suspend fun searchMovie(name:String)=api.searchMovie(name)
}