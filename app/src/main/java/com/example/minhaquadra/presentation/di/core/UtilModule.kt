package com.example.minhaquadra.presentation.di.core

import android.content.Context
import com.example.minhaquadra.data.util.Preferencias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    fun providePreferencias(@ApplicationContext appContext: Context) : Preferencias {
        return Preferencias(appContext)
    }



}