package com.mehmetalioyur.findmovieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehmetalioyur.findmovieapp.moviesmodel.Result


@Database(entities = [Result::class],version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao (): MoviesDao

}