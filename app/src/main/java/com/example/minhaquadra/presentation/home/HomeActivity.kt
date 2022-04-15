package com.example.minhaquadra.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Preferencias
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

    @Inject
    lateinit var preferencias: Preferencias

    lateinit var viewModel: HomeViewModel

    lateinit var binding: ActivityHomeBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, HomeFragment(), "fragment_name")
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, HomeFragment(), "fragment_name")
                        .commit()
                    true
                }
                R.id.agendaFragment -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.content, AgendaFragment(), "fragment_name")
                        .commit()
                    true
                }
                R.id.exit -> {
                    preferencias.clear()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

    }




}