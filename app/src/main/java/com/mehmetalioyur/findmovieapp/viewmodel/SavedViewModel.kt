package com.mehmetalioyur.findmovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetalioyur.findmovieapp.moviesmodel.Result
import com.mehmetalioyur.findmovieapp.repo.MoviesRepository
import com.mehmetalioyur.findmovieapp.repo.MoviesRepositoryInterface
import com.mehmetalioyur.findmovieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: MoviesRepositoryInterface
) : ViewModel() {

    private val _savedMovieList = MutableLiveData<List<Result>>()
    val savedMovieList : LiveData<List<Result>>
        get() =repository.getMovies()


    fun deleteMovie(movie: Result) = viewModelScope.launch {
        repository.deleteMovie(movie)
    }


}