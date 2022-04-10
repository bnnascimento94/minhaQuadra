package com.example.minhaquadra.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.ActivitySplashBinding
import com.example.minhaquadra.presentation.home.HomeActivity
import com.example.minhaquadra.presentation.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: SplashViewModelFactory

    lateinit var splashViewModel: SplashViewModel

    lateinit var binding:ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this,factory).get(SplashViewModel::class.java)
        splashViewModel.setSplashTimer()
        splashViewModel.splashTimer.observe(this, Observer { timerFinalizado->
            if(timerFinalizado){
                splashViewModel.connectedUser.observe(this, Observer {response ->
                    when(response){
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            if(response.message.equals("")){
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }else{
                                Snackbar.make(binding.root, response.message.toString(),Snackbar.LENGTH_LONG).show()
                            }
                        }
                        is Resource.Success ->{
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                    }
                })
            }
        })
    }



}