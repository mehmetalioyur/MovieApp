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


    init {

        _movieDetails.value = savedStateHandle["movieDetails"]
    }

    fun insertMovie() = viewModelScope.launch {

        _movieDetails.value?.let {
            repository.insertMovie(it)
        }
    }


}