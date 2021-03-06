package com.mehmetalioyur.findmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalioyur.findmovieapp.moviesmodel.MoviesModel
import com.mehmetalioyur.findmovieapp.repo.MoviesRepositoryInterface
import com.mehmetalioyur.findmovieapp.util.Constants.API_KEY
import com.mehmetalioyur.findmovieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val repository: MoviesRepositoryInterface
) : ViewModel() {


    private val _trendMovieList = MutableLiveData<Resource<MoviesModel>>()
    val trendMovieList : LiveData<Resource<MoviesModel>>
        get() = _trendMovieList


    init {
        getMovieFromApi()
    }





    private fun getMovieFromApi() {

        _trendMovieList.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repository.getTrendMovies(API_KEY, "en", "1" )
            _trendMovieList.value = response

        }
    }




}