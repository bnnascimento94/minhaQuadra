package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.data.repository.datasource.EquipeDataSource
import com.example.minhaquadra.data.repository.datasource.JogadorDataSource
import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.repository.datasource.PartidaDataSource
import com.example.minhaquadra.data.repository.datasourceimpl.EquipeDataSourceImpl
import com.example.minhaquadra.data.repository.equipe.EquipeRepositoryImpl
import com.example.minhaquadra.data.repository.jogador.JogadorRepositoryImpl
import com.example.minhaquadra.data.repository.login.LoginRepositoryImpl
import com.example.minhaquadra.data.repository.partida.PartidaRepositoryImpl
import com.example.minhaquadra.domain.repository.EquipeRepository
import com.example.minhaquadra.domain.repository.JogadorRepository
import com.example.minhaquadra.domain.repository.LoginRepository
import com.example.minhaquadra.domain.repository.PartidaRepository
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

    @Singleton
    @Provides
    fun providePartidaRepository(partidaDataSource: PartidaDataSource): PartidaRepository{
        return PartidaRepositoryImpl(partidaDataSource)
    }

    @Singleton
    @Provides
    fun provideEquipeRepository(equipeDataSource: EquipeDataSource): EquipeRepository{
        return EquipeRepositoryImpl(equipeDataSource)
    }

    @Singleton
    @Provides
    fun provideJogadorRepository(jogadorDataSource: JogadorDataSource): JogadorRepository{
        return JogadorRepositoryImpl(jogadorDataSource)
    }

}