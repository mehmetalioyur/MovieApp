package com.mehmetalioyur.findmovieapp.di

import android.content.Context
import androidx.room.Room
import com.mehmetalioyur.findmovieapp.database.MoviesDao
import com.mehmetalioyur.findmovieapp.database.MoviesDatabase
import com.mehmetalioyur.findmovieapp.repo.MoviesRepository
import com.mehmetalioyur.findmovieapp.repo.MoviesRepositoryInterface
import com.mehmetalioyur.findmovieapp.service.MoviesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, MoviesDatabase::class.java, "MoviesDB").build()

    @Singleton
    @Provides
    fun injectDao(database: MoviesDatabase) = database.moviesDao()

    @Singleton
    @Provides
    fun injectNormalRepo(dao : MoviesDao,api: MoviesAPI) = MoviesRepository(dao,api) as MoviesRepositoryInterface


}