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
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepositoryInterface
) : ViewModel() {

    private val _searchedMovieList = MutableLiveData<Resource<MoviesModel>>()
    val searchedMovieList: LiveData<Resource<MoviesModel>>
        get() = _searchedMovieList

    fun searchForMovie(searchQuery: String) {

        if (searchQuery.isEmpty()) {
            return
        }

        _searchedMovieList.value = Resource.loading(data = null)
        viewModelScope.launch {
            val response = repository.searchMovie(API_KEY, "en", searchQuery, "true")
            _searchedMovieList.value = response

        }
    }


}

