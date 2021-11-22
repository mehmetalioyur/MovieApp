package com.mehmetalioyur.findmovieapp.repo

import androidx.lifecycle.LiveData
import com.mehmetalioyur.findmovieapp.moviesmodel.MoviesModel
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.util.Resource
import retrofit2.Response

interface MoviesRepositoryInterface {

    suspend fun insertMovie(movie: Result)

    suspend fun deleteMovie(movie: Result)

    fun getMovies(): LiveData<List<Result>>

    suspend fun searchMovie(
        key: String,
        language: String,
        searchQuery: String,
        includeAdult: String
    ): Resource<MoviesModel>

    suspend fun getTrendMovies(
        apiKey: String,
        language: String,
        pageNumber: String
    ): Resource<MoviesModel>
}