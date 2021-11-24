package com.mehmetalioyur.findmovieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mehmetalioyur.findmovieapp.R
import com.mehmetalioyur.findmovieapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)



        val navHostFragment =supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
         binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

    }
}