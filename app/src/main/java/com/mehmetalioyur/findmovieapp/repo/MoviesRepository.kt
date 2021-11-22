package com.mehmetalioyur.findmovieapp.repo

import androidx.lifecycle.LiveData
import com.mehmetalioyur.findmovieapp.database.MoviesDao
import com.mehmetalioyur.findmovieapp.moviesmodel.MoviesModel
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.service.MoviesAPI
import com.mehmetalioyur.findmovieapp.util.Resource
import java.lang.Exception
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val api: MoviesAPI

) : MoviesRepositoryInterface {
    override suspend fun insertMovie(movie: Result) {
        moviesDao.insertMovies(movie)
    }

    override suspend fun deleteMovie(movie: Result) {
        moviesDao.deleteMovie(movie)
    }

    override fun getMovies(): LiveData<List<Result>> {
        return moviesDao.observeMovies()
    }

    override suspend fun searchMovie(
        key: String,
        language: String,
        searchQuery: String,
        includeAdult: String
    ): Resource<MoviesModel> {
        return try {
            val response = api.searchMovies(key, language, searchQuery, includeAdult)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("No data! ", null)
            } else {
                Resource.error("No data!", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data !", null)
        }
    }

    override suspend fun getTrendMovies(
        apiKey: String,
        language: String,
        pageNumber: String
    ): Resource<MoviesModel> {
        return try {
            val response = api.getPopularMovies(apiKey,language,pageNumber)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("No data! ", null)
            } else {
                Resource.error("No data!", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data !", null)
        }
    }


}