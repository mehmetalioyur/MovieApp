package com.mehmetalioyur.findmovieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mehmetalioyur.findmovieapp.moviesmodel.Result


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie : Result)

    @Delete //item sil
    suspend fun deleteMovie(movie: Result)

    @Query("SELECT * FROM movies")
    fun observeMovies(): LiveData<List<Result>>

    @Query("SELECT EXISTS(SELECT*FROM movies WHERE id = :id)")
    suspend fun isRowIsExist(id : Int) : Boolean






}