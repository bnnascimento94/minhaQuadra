package com.example.minhaquadra.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.ActivitySplashBinding
import com.example.minhaquadra.presentation.HomeActivity
import com.example.minhaquadra.presentation.login.LoginActivity
import com.example.minhaquadra.presentation.login.LoginViewModel
import com.example.minhaquadra.presentation.login.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: SplashViewModelFactory

    lateinit var splashViewModel: SplashViewModel

    lateinit var binding:ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this,factory).get(SplashViewModel::class.java)

        splashViewModel.splashTimer.observe(this, Observer { timerFinalizado->
            if(timerFinalizado){
                splashViewModel.connectedUser.observe(this, Observer {response ->
                    when(response){
                        is Resource.Loading ->{}
                        is Resource.Error ->{
                            if(response.message.equals("")){
                                startActivity(Intent(this, LoginActivity::class.java))
                            }else{
                                Snackbar.make(binding.root, response.message.toString(),Snackbar.LENGTH_LONG).show()
                            }
                        }
                        is Resource.Success ->{
                            startActivity(Intent(this, HomeActivity::class.java))
                        }
                    }
                })
            }
        })
    }


}