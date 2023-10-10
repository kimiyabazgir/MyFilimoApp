package com.example.myfilimoapp.ui.register

import com.example.myfilimoapp.models.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    //یه نکته مهم هست که چون BodyRegister یه data class هست زمانی که call میشه باید مقادیری که توی این fun داری مثل name,email,pass رو بهش توو ورودی بدی ولی چون این مقادیر رو نداریم موقع ثبت نام مقدار دیفالت استرینگ "" خالی رو میتونیم براشو ست کنیم توو data class ش که اینجا ارور نگیریم
    fun provideUserBody()=BodyRegister()
}