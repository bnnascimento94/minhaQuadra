package com.example.minhaquadra.presentation.di.core

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {


    @Singleton
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage{
        return FirebaseStorage.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }


    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth{
        return Firebase.auth
    }
}