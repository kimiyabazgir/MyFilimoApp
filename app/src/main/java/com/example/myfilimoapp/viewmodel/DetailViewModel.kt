package com.example.myfilimoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilimoapp.db.MovieEntity
import com.example.myfilimoapp.models.detail.ResponseDetail
import com.example.myfilimoapp.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) :ViewModel(){

    val detailMovie = MutableLiveData<ResponseDetail>()
    val loading =MutableLiveData<Boolean>()

    fun loadDetailMovie(id:Int) =viewModelScope.launch{
        loading.postValue(true)
        val response=repository.detailMovie(id)
        if (response.isSuccessful){
            detailMovie.postValue(response.body())
        }
        loading.postValue(false)
    }
    //Database
    //اینجا میخواهیم چک کنیم ببینیم فلان فیلم ایا favorite شده یا نه؟آیا در لیست favorite ها وجود دارد یا نه
    val isFavorite=MutableLiveData<Boolean>()
    suspend fun existsMovie(id: Int)= withContext(viewModelScope.coroutineContext){repository.existsMovie(id)}
    fun favoriteMovie(id: Int,entity: MovieEntity)= viewModelScope.launch {
        val exists=repository.existsMovie(id)
        if (exists){
            isFavorite.postValue(false)
            repository.deleteMovie(entity)
        }
        else{
            isFavorite.postValue(true)
            repository.insertMovie(entity)
        }
    }
}
//اینجا از withContext استفاده کردم حالا چرا؟از launch نمیخواستم استفاده کنم چون مقدار id رو میخوام بهم برگردونه و ازش استفاده کنم پس از asyc هم میتونستم استفاده کنم چرا نکردم؟و با await اون چیزی که میخواستم بگیرم رو نگرفتم؟
//چون await یعنی همون لحظه میخوای اون چیزی که برمیگرده رو بگیری ولی الان همون لحظه مستقیم نمیخوای بگیری و بعدا میخوای بری توو detailFragment بگیری ازش استفاده کنی پس بهتره از withContext استفاده کنی
//حالا از withContext یه استفاده دیگه هم میشه کرد اینکه در لحظه میخوای مثلا بری روو io یا main یا defult یعنی توو لحظه thread تو میتونی تغییر بدی