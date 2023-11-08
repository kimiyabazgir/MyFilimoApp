package com.example.myfilimoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilimoapp.db.MovieEntity
import com.example.myfilimoapp.models.home.ResponseMoviesList
import com.example.myfilimoapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository):ViewModel() {
    //چون getallmovies است یه دونه فیلم نیست پس لیستی از همه فیلم ها است
    //چون توو dao ات از نوع mutable list است اینجا هم mutablelist تعریفش کردیم
    val favoriteList= MutableLiveData<MutableList<MovieEntity>>()
    val empty= MutableLiveData<Boolean>()

    fun loadFavoriteList()=viewModelScope.launch() {
        val list=repository.allFavoriteList()
        //این میگه اگر لیستت خالی نبود بیا این list رو برای من برگردون
        if(list.isNotEmpty()){
               favoriteList.postValue(list)
                //پس حالا که خالی نیست امپتی لیست رو نشون نده
                empty.postValue(false)
            }
            else{
                //وگرنه حالا که خالیه امپتی لیست رو نشون بده که ینی خالیه
                empty.postValue(true)
            }
        }
}