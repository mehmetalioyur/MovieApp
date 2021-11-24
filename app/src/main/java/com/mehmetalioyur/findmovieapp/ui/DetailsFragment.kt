package com.mehmetalioyur.findmovieapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehmetalioyur.findmovieapp.databinding.FragmentDetailsBinding
import com.mehmetalioyur.findmovieapp.util.Constants
import com.mehmetalioyur.findmovieapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: DetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val navBar: BottomNavigationView = activity!!.findViewById(com.mehmetalioyur.findmovieapp.R.id.bottomNavigationView)
        navBar.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()


        binding.fab.setOnClickListener {
            viewModel.insertMovie()
            Toast.makeText(requireContext(), "Movie Saved", Toast.LENGTH_SHORT).show()
            binding.fab.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun subscribeToObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner, {

            glide.load("${Constants.POSTER_BASE_URL}${it.poster_path}").into(binding.imageDetail)
            binding.movieName.text = it.original_title
            binding.releaseDateText.text = "Release Date : ${it.release_date}"
            binding.movieDescriptionText.text = "TOPIC \n  ${it.overview}"
            binding.originalLanguageText.text = "Language : ${it.original_language}"
            binding.averageVoteText.text = " Rating : ${it.vote_average.toString()}"

        })
        viewModel.isSavedBefore.observe(viewLifecycleOwner, {
            if (it == true) {
                binding.fab.visibility = View.GONE
            } else
                binding.fab.visibility = View.VISIBLE

        })


    }


    override fun onDestroyView() {

        super.onDestroyView()
        val navBar: BottomNavigationView = activity!!.findViewById(com.mehmetalioyur.findmovieapp.R.id.bottomNavigationView)
        navBar.visibility = View.VISIBLE
        _binding = null

    }

}