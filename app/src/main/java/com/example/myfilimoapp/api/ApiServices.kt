package com.example.myfilimoapp.api

import com.example.myfilimoapp.models.ResponseGenresList
import com.example.myfilimoapp.models.home.ResponseMoviesList
import com.example.myfilimoapp.models.register.BodyRegister
import com.example.myfilimoapp.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    @POST("Register")
    suspend fun registerUser(@Body body:BodyRegister) : Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun moviesTopList(@Path ("genre_id") id:Int) :Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList() :Response<ResponseGenresList>

    @GET("movies")
    suspend fun moviesLastList():Response<ResponseMoviesList>

}