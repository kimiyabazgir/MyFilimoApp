package com.example.myfilimoapp.repository

import com.example.myfilimoapp.api.ApiServices
import com.example.myfilimoapp.db.MovieEntity
import com.example.myfilimoapp.db.MoviesDao
import com.example.myfilimoapp.models.detail.ResponseDetail
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api:ApiServices,private val dao: MoviesDao) {
    //Api
    suspend  fun detailMovie(id:Int)=api.detailMovie(id)
    //Database
    suspend fun insertMovie(entity: MovieEntity)=dao.insertMovies(entity)
    suspend fun deleteMovie(entity: MovieEntity)=dao.deteleMovies(entity)
    suspend fun existsMovie(id: Int)=dao.exitsMovie(id)
}