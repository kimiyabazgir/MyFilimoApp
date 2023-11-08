package com.example.myfilimoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilimoapp.models.ResponseGenresList
import com.example.myfilimoapp.models.home.ResponseMoviesList
import com.example.myfilimoapp.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository):ViewModel(){
    // رو تهش گذاشتیم که initialize شه()
    val topMoviesList=MutableLiveData<ResponseMoviesList>()
    val genresList=MutableLiveData<ResponseGenresList>()
    val lastMoviesList=MutableLiveData<ResponseMoviesList>()
    val loading=MutableLiveData<Boolean>()
//برای لود شدن لیست فیلم ها
    fun loadTopMoviesList(id:Int)=viewModelScope.launch {
      val response=repository.topMoviesList(id)
        if (response.isSuccessful){
            topMoviesList.postValue(response.body())
        }

    }
//برای لود شدن لیست ژانر ها
    fun loadGenresList()=viewModelScope.launch {
        val response=repository.genresList()
        if (response.isSuccessful){
            genresList.postValue(response.body())
        }
    }
//برای لود شدن لیست آخرین فیلم ها
    fun loadLastMoviesList()=viewModelScope.launch {
    loading.postValue(true)
        val response=repository.lastMoviesList()
        if (response.isSuccessful){
            lastMoviesList.postValue(response.body())
    }
    loading.postValue(false)
}
}