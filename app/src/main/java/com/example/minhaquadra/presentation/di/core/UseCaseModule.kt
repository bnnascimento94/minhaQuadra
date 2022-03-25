package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.domain.repository.EquipeRepository
import com.example.minhaquadra.domain.repository.JogadorRepository
import com.example.minhaquadra.domain.repository.LoginRepository
import com.example.minhaquadra.domain.repository.PartidaRepository
import com.example.minhaquadra.domain.usercases.equipe.*
import com.example.minhaquadra.domain.usercases.jogador.DeleteJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.ListJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.RegisterJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.UpdateJogadorUsercase
import com.example.minhaquadra.domain.usercases.login.ForgotPasswordUsercase
import com.example.minhaquadra.domain.usercases.login.GetLoginUsercase
import com.example.minhaquadra.domain.usercases.login.RegisterUsercase
import com.example.minhaquadra.domain.usercases.login.VerifyUserUsercase
import com.example.minhaquadra.domain.usercases.partida.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun providesVerifyUsercase(loginRepository: LoginRepository): VerifyUserUsercase {
        return VerifyUserUsercase(loginRepository)
    }

    @Singleton
    @Provides
    fun providesGetLoginUsercase(loginRepository: LoginRepository): GetLoginUsercase {
        return GetLoginUsercase(loginRepository)
    }


    @Singleton
    @Provides
    fun providesForgotPasswordUsercase(loginRepository: LoginRepository): ForgotPasswordUsercase {
        return ForgotPasswordUsercase(loginRepository)
    }

    @Singleton
    @Provides
    fun providesRegisterUsercase(loginRepository: LoginRepository): RegisterUsercase {
        return RegisterUsercase(loginRepository)
    }

    @Singleton
    @Provides
    fun providesRegisterUsercase(equipeRepository: EquipeRepository): GetEquipeUsercase {
        return GetEquipeUsercase(equipeRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteEquipeUsercase(equipeRepository: EquipeRepository): DeleteEquipeUsercase {
        return DeleteEquipeUsercase(equipeRepository)
    }

    @Singleton
    @Provides
    fun providesListEquipeUsercase(equipeRepository: EquipeRepository): ListEquipesUsercase {
        return ListEquipesUsercase(equipeRepository)
    }

    @Singleton
    @Provides
    fun providesRegisterEquipeUsercase(equipeRepository: EquipeRepository): RegisterEquipeUsercase {
        return RegisterEquipeUsercase(equipeRepository)
    }

    @Singleton
    @Provides
    fun providesUpdateEquipeUsercase(equipeRepository: EquipeRepository): UpdateEquipeUsercase {
        return UpdateEquipeUsercase(equipeRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteJogadorUsercase(jogadorRepository: JogadorRepository): DeleteJogadorUsercase {
        return DeleteJogadorUsercase(jogadorRepository)
    }

    @Singleton
    @Provides
    fun providesListJogadorUsercase(jogadorRepository: JogadorRepository): ListJogadorUsercase {
        return ListJogadorUsercase(jogadorRepository)
    }

    @Singleton
    @Provides
    fun providesRegisterJogadorUsercase(jogadorRepository: JogadorRepository): RegisterJogadorUsercase {
        return RegisterJogadorUsercase(jogadorRepository)
    }

    @Singleton
    @Provides
    fun providesUpdateJogadorUsercase(jogadorRepository: JogadorRepository): UpdateJogadorUsercase {
        return UpdateJogadorUsercase(jogadorRepository)
    }

    @Singleton
    @Provides
    fun providesDeletePartidaUsercase(partidaRepository: PartidaRepository): DeletePartidaUsercase {
        return DeletePartidaUsercase(partidaRepository)
    }

    @Singleton
    @Provides
    fun providesListaPartidaUsercase(partidaRepository: PartidaRepository): ListPartidaUsercase {
        return ListPartidaUsercase(partidaRepository)
    }

    @Singleton
    @Provides
    fun providesListaPartidaByEquipeUsercase(partidaRepository: PartidaRepository): ListaPartidaByEquipeUsercase {
        return ListaPartidaByEquipeUsercase(partidaRepository)
    }

    @Singleton
    @Provides
    fun providesListaPartidaByDataUsercase(partidaRepository: PartidaRepository): ListaPartidaByDataUsercase {
        return ListaPartidaByDataUsercase(partidaRepository)
    }

    @Singleton
    @Provides
    fun providesRegisterPartidaUsercase(partidaRepository: PartidaRepository): RegisterPartidaUsercase {
        return RegisterPartidaUsercase(partidaRepository)
    }

    @Singleton
    @Provides
    fun providesUpdatePartidaUsercase(partidaRepository: PartidaRepository): UpdatePartidaUserCase {
        return UpdatePartidaUserCase(partidaRepository)
    }



}