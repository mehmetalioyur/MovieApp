package com.mehmetalioyur.findmovieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mehmetalioyur.findmovieapp.moviesmodel.Result


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //item ekle
    suspend fun insertMovies(movie : Result)

    @Delete //item sil
    suspend fun deleteMovie(movie: Result)

    @Query("SELECT * FROM movies")   // herşeyi gözlemle
    fun observeMovies(): LiveData<List<Result>>




}