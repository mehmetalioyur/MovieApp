package com.mehmetalioyur.findmovieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mehmetalioyur.findmovieapp.R
import com.mehmetalioyur.findmovieapp.adapter.MoviesRecyclerAdapter
import com.mehmetalioyur.findmovieapp.databinding.FragmentTrendsBinding
import com.mehmetalioyur.findmovieapp.util.Status
import com.mehmetalioyur.findmovieapp.viewmodel.TrendsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendsFragment : Fragment() {
    private var _binding: FragmentTrendsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var moviesRecyclerAdapter: MoviesRecyclerAdapter


    private val viewModel: TrendsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.trendsRecyclerView.adapter = moviesRecyclerAdapter
        binding.trendsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        moviesRecyclerAdapter.setOnItemClickListener {
            val action = TrendsFragmentDirections.actionTrendsFragmentToDetailsFragment(it)
            findNavController().navigate(action)
        }

        subscribeToObservers()


    }

    private fun subscribeToObservers() {
        viewModel.trendMovieList.observe(viewLifecycleOwner, {
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
        binding.trendsRecyclerView.visibility = View.GONE
    }

    private fun showScreen() {
        binding.trendsRecyclerView.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.trendsProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.trendsProgressBar.visibility = View.VISIBLE
    }

    private fun hideErrorMessage() {
        binding.trendsErrorMessage.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.trendsErrorMessage.visibility = View.VISIBLE
        binding.trendsErrorMessage.text = message

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}