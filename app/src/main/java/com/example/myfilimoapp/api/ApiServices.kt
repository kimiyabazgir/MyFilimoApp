package com.example.myfilimoapp.api

import com.example.myfilimoapp.models.ResponseGenresList
import com.example.myfilimoapp.models.detail.ResponseDetail
import com.example.myfilimoapp.models.home.ResponseMoviesList
import com.example.myfilimoapp.models.register.BodyRegister
import com.example.myfilimoapp.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    @POST("Register")
    suspend fun registerUser(@Body body:BodyRegister) : Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun moviesTopList(@Path ("genre_id") id:Int) :Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList() :Response<ResponseGenresList>

    @GET("movies")
    suspend fun moviesLastList():Response<ResponseMoviesList>
// کیو همون نام فیلمه که من دارممیفرستم سمت سرور که حالا اطلاعاتشو بهم بده
    @GET("movies")
    suspend fun searchMovie(@Query("q") name:String):Response<ResponseMoviesList>

    @GET("movies/{movie_id}")
    suspend fun detailMovie(@Path("movie_id") id:Int):Response<ResponseDetail>

}