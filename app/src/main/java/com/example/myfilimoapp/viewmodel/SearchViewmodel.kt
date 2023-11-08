package com.example.myfilimoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilimoapp.models.home.ResponseMoviesList
import com.example.myfilimoapp.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(private val repository: SearchRepository) :ViewModel() {
    val moviesList=MutableLiveData<ResponseMoviesList>()
    val loading =MutableLiveData<Boolean>()
    val empty=MutableLiveData<Boolean>()

    fun loadSearchMovies(name:String)=viewModelScope.launch {
        //لودینگ شروع به کار کنه
        loading.postValue(true)
        val response=repository.searchMovie(name)
        //این میگه اگر ریسپانست اوکی بود
        if(response.isSuccessful){
            //اگر ریسپانس بادی خالی نبود
            //این data همون لیستمونه که توو ریسپانسه
            if(response.body()?.data!!.isNotEmpty()){
                //پس بیا برای مووی لیست من ریسپانس بادی رو بفرست
                moviesList.postValue(response.body())
                //پس حالا که خالی نیست امپتی لیست رو نشون نده
                empty.postValue(false)
            }
            else{
                //وگرنه حالا که خالیه امپتی لیست رو نشون بده که ینی خالیه
                empty.postValue(true)
            }
        }
            loading.postValue(false)

    }
}