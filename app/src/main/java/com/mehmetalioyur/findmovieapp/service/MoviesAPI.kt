package com.mehmetalioyur.findmovieapp.service

import com.mehmetalioyur.findmovieapp.moviesmodel.MoviesModel
import com.mehmetalioyur.findmovieapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    //popularMovies

    @GET("movie/popular?")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String, //isterim ileride
        @Query("page") pageNumber: String // int-> integer kabul ediyor mu bilmiyorum.
    ): Response<MoviesModel>

    //searchMovies
    @GET("search/movie?")
    suspend fun searchMovies(
        @Query("api_key") key: String = API_KEY,
        @Query("language") language: String, //isterim ileride
        @Query("query") searchQuery: String,
        @Query("include_adult") includeAdult: String //bool--> belki bool kabul etmiyodur.Denerim

    ): Response<MoviesModel>

}