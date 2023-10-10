package com.example.myfilimoapp.utils

import android.content.Context
import android.preference.PreferenceDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StoreUserData @Inject constructor(@ApplicationContext val context: Context) {

    //ازcompanion object استفاده کردیم که بتونیم جاهای دیگه هم از دیتااستورمون استفاه کنیم
    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(Constants.USER_INFO_DATASTORE)
        val userToken= stringPreferencesKey(Constants.USER_TOKEN)
    }

    //برای متد ذخیره اطلاعات در دیتا استور از suspend funاستفاده می کنیم
    //از context برای دسترسی به datastore استفاده می کنیم
    suspend fun saveUserToken(token:String){
        context.dataStore.edit{
           //به it کلیدمون رو میدیم و میگیم توکنی که گرفتیو بریز توو کلید ذخیره کن
         it[userToken]=token
        }
    }

     //برای get کردن اطلاعات در دیتا استور نیازی نیست از suspend fun استفاده کنیم
    //با map برای من برمی گردونه userToken ذخیره شده رو
    fun getUserToken()=context.dataStore.data.map{
        it[userToken]?:""
    }

}
