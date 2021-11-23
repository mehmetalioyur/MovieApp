package com.mehmetalioyur.findmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetalioyur.findmovieapp.adapter.MoviesRecyclerAdapter
import com.mehmetalioyur.findmovieapp.databinding.FragmentSavedBinding
import com.mehmetalioyur.findmovieapp.viewmodel.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment() : Fragment() {

    @Inject
    lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter

    private val viewModel: SavedViewModel by viewModels()

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedMovie = moviesRecyclerAdapter.movies[layoutPosition]
                viewModel.deleteMovie(selectedMovie)
                Toast.makeText(requireContext(), "Movie Deleted", Toast.LENGTH_SHORT).show()
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.savedRecyclerView.adapter = moviesRecyclerAdapter
        binding.savedRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        moviesRecyclerAdapter.setOnItemClickListener {
            val action = SavedFragmentDirections.actionSavedFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
        subscribeToObservers()


        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.savedRecyclerView)
    }

    private fun subscribeToObservers() {
        viewModel.savedMovieList.observe(viewLifecycleOwner, {
            moviesRecyclerAdapter.movies = it
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}