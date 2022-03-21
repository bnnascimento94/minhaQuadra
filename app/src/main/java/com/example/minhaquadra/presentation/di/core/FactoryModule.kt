package com.example.minhaquadra.presentation.di.core

import android.app.Application
import com.example.minhaquadra.domain.usercases.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.RegisterUsercase
import com.example.minhaquadra.domain.usercases.VerifyUserUsercase
import com.example.minhaquadra.presentation.login.LoginViewModelFactory
import com.example.minhaquadra.presentation.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun loginViewModelFactory(
        getLoginUsercase: GetLoginUsercase,
        forgotPasswordUsercase: ForgotPasswordUsercase,
        registerUsercase: RegisterUsercase
    ): LoginViewModelFactory{
        return LoginViewModelFactory(getLoginUsercase,forgotPasswordUsercase,registerUsercase)
    }


    @Singleton
    @Provides
    fun splashViewModelFactory(
        verifyUserUsercase: VerifyUserUsercase
    ): SplashViewModelFactory {
        return SplashViewModelFactory(verifyUserUsercase)
    }

}