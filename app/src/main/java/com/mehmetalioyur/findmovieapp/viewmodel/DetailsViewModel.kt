package com.mehmetalioyur.findmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.repo.MoviesRepositoryInterface
import com.mehmetalioyur.findmovieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MoviesRepositoryInterface,

) : ViewModel() {


    private val _movieDetails = MutableLiveData<Result>()
    val movieDetails: LiveData<Result>
        get() =_movieDetails



    init{


    }
    fun getMovieDetails(movieDetails : Result){
        _movieDetails.value = movieDetails
    }

    fun insertMovie() = viewModelScope.launch {
        //save işlemi viewmodel 20. dkaikada
        _movieDetails.value?.let {
            repository.insertMovie(it) }
    }



}