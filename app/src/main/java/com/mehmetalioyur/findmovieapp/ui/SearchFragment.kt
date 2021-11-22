package com.mehmetalioyur.findmovieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehmetalioyur.findmovieapp.adapter.MoviesRecyclerAdapter
import com.mehmetalioyur.findmovieapp.databinding.FragmentSearchBinding
import com.mehmetalioyur.findmovieapp.util.Status
import com.mehmetalioyur.findmovieapp.viewmodel.SearchViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchFragment@Inject constructor(
    private val moviesRecyclerAdapter: MoviesRecyclerAdapter

) : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel : SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]



        binding.searchRecyclerView.adapter = moviesRecyclerAdapter
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        var job : Job? = null

        binding.searchMovieEditText.addTextChangedListener{
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                   if(it.toString().isNotEmpty()){
                       viewModel.searchForMovie(it.toString())
                   }
                }
            }
        }


        moviesRecyclerAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }
        subscribeToObservers()
    }


    private fun subscribeToObservers(){
        viewModel.searchedMovieList.observe(viewLifecycleOwner, {

            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { movieList ->
                        val movies = movieList.results
                        showScreen()
                        hideErrorMessage()
                        hideProgressBar()
                       moviesRecyclerAdapter.movies = movies
                    }


                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    hideScreen()
                    hideProgressBar()
                    showErrorMessage("Veri yok")

                }

                Status.LOADING -> {
                    hideScreen()
                    showProgressBar()
                    hideErrorMessage()

                }
            }

        })





    }

    private fun hideScreen() {
        binding.searchRecyclerView.visibility = View.GONE
    }

    private fun showScreen() {
        binding.searchRecyclerView.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.searchProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.searchProgressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.searchErrorMessage.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.searchErrorMessage.visibility = View.VISIBLE
        binding.searchErrorMessage.text = message

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}