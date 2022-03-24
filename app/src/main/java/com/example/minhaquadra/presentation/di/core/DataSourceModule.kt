package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.data.repository.datasource.EquipeDataSource
import com.example.minhaquadra.data.repository.datasource.JogadorDataSource
import com.example.minhaquadra.data.repository.datasource.LoginDataSource
import com.example.minhaquadra.data.repository.datasource.PartidaDataSource
import com.example.minhaquadra.data.repository.datasourceimpl.EquipeDataSourceImpl
import com.example.minhaquadra.data.repository.datasourceimpl.JogadorDataSourceImpl
import com.example.minhaquadra.data.repository.datasourceimpl.LoginDataSourceImpl
import com.example.minhaquadra.data.repository.datasourceimpl.PartidaDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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

    @Singleton
    @Provides
    fun provideEquipeDataSource(database: FirebaseFirestore,firebaseStorage: FirebaseStorage) : EquipeDataSource {
        return EquipeDataSourceImpl(database, firebaseStorage)
    }

    @Singleton
    @Provides
    fun provideJogadorDataSource(database: FirebaseFirestore) : JogadorDataSource {
        return JogadorDataSourceImpl(database)
    }

    @Singleton
    @Provides
    fun providePartidaDataSource(database: FirebaseFirestore) : PartidaDataSource {
        return PartidaDataSourceImpl(database)
    }

}