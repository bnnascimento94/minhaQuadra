package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.repository.login.LoginRepositoryImpl
import com.example.minhaquadra.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(loginDataSource: LoginDataSource): LoginRepository{
        return LoginRepositoryImpl(loginDataSource)
    }

}