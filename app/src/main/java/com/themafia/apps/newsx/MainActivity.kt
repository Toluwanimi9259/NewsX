package com.themafia.apps.newsx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.themafia.apps.newsx.databinding.ActivityMainBinding
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModel
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : NewsViewModelFactory

    lateinit var newsViewModel : NewsViewModel

    lateinit var binding : ActivityMainBinding

    lateinit var navHostFragment : NavHostFragment

    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        newsViewModel = ViewModelProvider(this , viewModelFactory)[NewsViewModel::class.java]

    }
}