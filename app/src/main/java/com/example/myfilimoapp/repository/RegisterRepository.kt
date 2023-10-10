package com.example.myfilimoapp.repository

import com.example.myfilimoapp.api.ApiServices
import com.example.myfilimoapp.models.register.BodyRegister
import retrofit2.http.Body
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api:ApiServices) {
    suspend fun registerUser(body: BodyRegister)=api.registerUser(body)
}