package com.example.myfilimoapp.db

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.myfilimoapp.utils.Constants
import retrofit2.http.DELETE
import retrofit2.http.Query

//بهتره برای اینکه میخواهیم یه عملی رو انجام بدیم از ساسپند فانکشن ها استفاده کنیم مثلا میخواهیم لیست گت کنیم نمیخواد ساسپند فانکشن استفاده کنیم
@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(entity: MovieEntity)

   @Delete
    suspend fun deteleMovies(entity: MovieEntity)


    @androidx.room.Query("SELECT * FROM ${Constants.MOVIES_TABLE}")
    fun getAllMovies():MutableList<MovieEntity>
//میگم اگر id برابر بود با اون id که من ازت می خوام ینی id که من توو ورودی بهت میدم
    //قضیه exist اینه که ایا همچین چیزی(داخل پرانتز) هست یا نه اگر هست برگردون
    @androidx.room.Query("SELECT EXISTS (SELECT 1 FROM ${Constants.MOVIES_TABLE} WHERE id= :movieId)")
    suspend fun exitsMovie(movieId:Int):Boolean


}