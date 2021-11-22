package com.mehmetalioyur.findmovieapp.moviesmodel

data class MoviesModel(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)