package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.presentation.home.adapter.JogadorAdapter
import com.example.minhaquadra.presentation.home.adapter.PartidasAdapter
import com.example.minhaquadra.presentation.home.adapter.PartidasDoDiaAdapter
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
    fun providePartidasAdapter(): PartidasAdapter {
        return PartidasAdapter()
    }

    @Singleton
    @Provides
    fun provideJogadoresAdapter(): JogadorAdapter {
        return JogadorAdapter()
    }

    @Singleton
    @Provides
    fun providePartidasDoDiaAdapter(): PartidasDoDiaAdapter {
        return PartidasDoDiaAdapter()
    }

}