package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.domain.repository.LoginRepository
import com.example.minhaquadra.domain.usercases.login.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.login.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.login.RegisterUsercase
import com.example.minhaquadra.domain.usercases.login.VerifyUserUsercase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesVerifyUsercase(loginRepository: LoginRepository): VerifyUserUsercase {
        return VerifyUserUsercase(loginRepository)
    }

    @Provides
    fun providesGetLoginUsercase(loginRepository: LoginRepository): GetLoginUsercase {
        return GetLoginUsercase(loginRepository)
    }


    @Provides
    fun providesForgotPasswordUsercase(loginRepository: LoginRepository): ForgotPasswordUsercase {
        return ForgotPasswordUsercase(loginRepository)
    }

    @Provides
    fun providesRegisterUsercase(loginRepository: LoginRepository): RegisterUsercase {
        return RegisterUsercase(loginRepository)
    }


}