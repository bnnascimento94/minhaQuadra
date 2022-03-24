package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.domain.usercases.equipe.DeleteEquipeUsercase
import com.example.minhaquadra.domain.usercases.equipe.ListEquipesUsercase
import com.example.minhaquadra.domain.usercases.equipe.RegisterEquipeUsercase
import com.example.minhaquadra.domain.usercases.equipe.UpdateEquipeUsercase
import com.example.minhaquadra.domain.usercases.jogador.DeleteJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.ListJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.RegisterJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.UpdateJogadorUsercase
import com.example.minhaquadra.domain.usercases.login.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.login.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.login.RegisterUsercase
import com.example.minhaquadra.domain.usercases.login.VerifyUserUsercase
import com.example.minhaquadra.domain.usercases.partida.*
import com.example.minhaquadra.presentation.home.HomeViewModelFactory
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

    @Singleton
    @Provides
    fun homeViewModelFactory(
        deleteEquipeUsercase: DeleteEquipeUsercase,
        listEquipesUsercase: ListEquipesUsercase,
        registerEquipeUsercase: RegisterEquipeUsercase,
        updateEquipeUsercase: UpdateEquipeUsercase,
        deleteJogadorUsercase: DeleteJogadorUsercase,
        listJogadorUsercase: ListJogadorUsercase,
        registerJogadorUsercase: RegisterJogadorUsercase,
        updateJogadorUsercase: UpdateJogadorUsercase,
        deletePartidaUsercase: DeletePartidaUsercase,
        listPartidaUsercase: ListPartidaUsercase,
        listaPartidaByDataUsercase: ListaPartidaByDataUsercase,
        registerPartidaUsercase: RegisterPartidaUsercase,
        updatePartidaUserCase: UpdatePartidaUserCase
    ): HomeViewModelFactory {

        return HomeViewModelFactory(
            deleteEquipeUsercase,
            listEquipesUsercase,
            registerEquipeUsercase,
            updateEquipeUsercase,
            deleteJogadorUsercase,
            listJogadorUsercase,
            registerJogadorUsercase,
            updateJogadorUsercase,
            deletePartidaUsercase,
            listPartidaUsercase,
            listaPartidaByDataUsercase,
            registerPartidaUsercase,
            updatePartidaUserCase
        )

    }

}