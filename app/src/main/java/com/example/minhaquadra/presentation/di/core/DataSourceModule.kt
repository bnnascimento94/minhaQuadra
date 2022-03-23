package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.repository.datasourceimpl.LoginDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideLoginDataSource(firebaseAuth: FirebaseAuth) : LoginDataSource {
        return LoginDataSourceImpl(firebaseAuth)
    }



}