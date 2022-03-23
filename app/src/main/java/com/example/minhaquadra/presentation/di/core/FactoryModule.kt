package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.domain.usercases.login.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.login.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.login.RegisterUsercase
import com.example.minhaquadra.domain.usercases.login.VerifyUserUsercase
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