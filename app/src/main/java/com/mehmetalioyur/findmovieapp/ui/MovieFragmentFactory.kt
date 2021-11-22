package com.mehmetalioyur.findmovieapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.mehmetalioyur.findmovieapp.adapter.MoviesRecyclerAdapter
import javax.inject.Inject

class MovieFragmentFactory @Inject constructor(
    private val moviesRecyclerAdapter: MoviesRecyclerAdapter,
    private val glide: RequestManager,

    ) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            TrendsFragment::class.java.name -> TrendsFragment(moviesRecyclerAdapter)
            SearchFragment::class.java.name -> SearchFragment(moviesRecyclerAdapter)
            SavedFragment::class.java.name -> SavedFragment(moviesRecyclerAdapter)
            DetailsFragment::class.java.name -> DetailsFragment (glide)
            else -> super.instantiate(classLoader, className)
        }


    }


}