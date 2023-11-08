package com.example.myfilimoapp.repository

import androidx.room.Insert
import com.example.myfilimoapp.db.MoviesDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao:MoviesDao) {

    fun allFavoriteList()=dao.getAllMovies()
}