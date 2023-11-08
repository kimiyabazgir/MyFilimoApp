package com.example.myfilimoapp.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.example.myfilimoapp.db.MovieEntity
import com.example.myfilimoapp.db.MoviesDatabase
import com.example.myfilimoapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context)= Room.databaseBuilder(context,MoviesDatabase::class.java,Constants.MOVIES_TABLE)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db:MoviesDatabase)=db.movieDao()

    @Provides
    @Singleton
    fun provideEntity()=MovieEntity()
}