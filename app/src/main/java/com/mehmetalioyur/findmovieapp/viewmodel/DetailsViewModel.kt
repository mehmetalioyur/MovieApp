package com.mehmetalioyur.findmovieapp.viewmodel

import androidx.lifecycle.*
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.repo.MoviesRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MoviesRepositoryInterface,
     savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _movieDetails = MutableLiveData<Result>()
    val movieDetails: LiveData<Result>
        get() = _movieDetails


    private val _savedMovieList = MutableLiveData<List<Result>>()
    val savedMovieList: LiveData<List<Result>>
        get() = _savedMovieList

    private val _isSavedBefore = MutableLiveData(false)
    val isSavedBefore : LiveData<Boolean>
    get() = _isSavedBefore


    init {
        _savedMovieList.value = repository.getMovies().value
        _movieDetails.value = savedStateHandle["movieDetails"]
        isMovieExist()
    }

    fun insertMovie() = viewModelScope.launch {
        _movieDetails.value?.let {
            repository.insertMovie(it)
        }
    }

    fun isMovieExist() = viewModelScope.launch{
       _isSavedBefore.value =  repository.isRowIsExists(movieDetails.value!!.id!!)
        println(_isSavedBefore.value)
    }


}