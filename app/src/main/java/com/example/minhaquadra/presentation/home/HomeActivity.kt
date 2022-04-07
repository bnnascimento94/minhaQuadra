package com.example.minhaquadra.presentation.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.minhaquadra.R
import com.example.minhaquadra.databinding.ActivityHomeBinding
import com.example.minhaquadra.presentation.login.LoginActivity
import com.example.minhaquadra.presentation.login.LoginViewModel
import com.example.minhaquadra.presentation.login.LoginViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: HomeViewModelFactory

    lateinit var viewModel: HomeViewModel

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationViewHome) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.exit -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    onDestroy()
                    true
                }
                else -> false
            }
        }

    }




}