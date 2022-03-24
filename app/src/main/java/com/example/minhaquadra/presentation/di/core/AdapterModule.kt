package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.presentation.home.HomeAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideNewsAdapter(): HomeAdapter {
        return HomeAdapter()
    }


}