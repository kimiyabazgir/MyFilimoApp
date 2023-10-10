package com.example.myfilimoapp.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfilimoapp.R
import com.example.myfilimoapp.models.register.BodyRegister
import com.example.myfilimoapp.models.register.ResponseRegister
import com.example.myfilimoapp.repository.RegisterRepository
import com.example.myfilimoapp.ui.register.RegisterFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {
    val registerUser=MutableLiveData<ResponseRegister>()
    val loading=MutableLiveData<Boolean>()

    fun sendRegisterUser(body:BodyRegister)=viewModelScope.launch {
        loading.postValue(true)
        val response=repository.registerUser(body)
        if (response.isSuccessful){
            registerUser.postValue(response.body())
        }
        loading.postValue(false)
    }
}