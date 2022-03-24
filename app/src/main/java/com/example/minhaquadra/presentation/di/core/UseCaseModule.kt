package com.example.minhaquadra.presentation.di.core

import com.example.minhaquadra.domain.repository.EquipeRepository
import com.example.minhaquadra.domain.repository.JogadorRepository
import com.example.minhaquadra.domain.repository.LoginRepository
import com.example.minhaquadra.domain.repository.PartidaRepository
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun providesVerifyUsercase(loginRepository: LoginRepository): VerifyUserUsercase {
        return VerifyUserUsercase(loginRepository)
    }

    @Provides
    fun providesGetLoginUsercase(loginRepository: LoginRepository): GetLoginUsercase {
        return GetLoginUsercase(loginRepository)
    }


    @Provides
    fun providesForgotPasswordUsercase(loginRepository: LoginRepository): ForgotPasswordUsercase {
        return ForgotPasswordUsercase(loginRepository)
    }

    @Provides
    fun providesRegisterUsercase(loginRepository: LoginRepository): RegisterUsercase {
        return RegisterUsercase(loginRepository)
    }

    @Provides
    fun providesDeleteEquipeUsercase(equipeRepository: EquipeRepository): DeleteEquipeUsercase {
        return DeleteEquipeUsercase(equipeRepository)
    }

    @Provides
    fun providesListEquipeUsercase(equipeRepository: EquipeRepository): ListEquipesUsercase {
        return ListEquipesUsercase(equipeRepository)
    }

    @Provides
    fun providesRegisterEquipeUsercase(equipeRepository: EquipeRepository): RegisterEquipeUsercase {
        return RegisterEquipeUsercase(equipeRepository)
    }

    @Provides
    fun providesUpdateEquipeUsercase(equipeRepository: EquipeRepository): UpdateEquipeUsercase {
        return UpdateEquipeUsercase(equipeRepository)
    }

    @Provides
    fun providesDeleteJogadorUsercase(jogadorRepository: JogadorRepository): DeleteJogadorUsercase {
        return DeleteJogadorUsercase(jogadorRepository)
    }

    @Provides
    fun providesListJogadorUsercase(jogadorRepository: JogadorRepository): ListJogadorUsercase {
        return ListJogadorUsercase(jogadorRepository)
    }

    @Provides
    fun providesRegisterJogadorUsercase(jogadorRepository: JogadorRepository): RegisterJogadorUsercase {
        return RegisterJogadorUsercase(jogadorRepository)
    }

    @Provides
    fun providesUpdateJogadorUsercase(jogadorRepository: JogadorRepository): UpdateJogadorUsercase {
        return UpdateJogadorUsercase(jogadorRepository)
    }

    @Provides
    fun providesDeletePartidaUsercase(partidaRepository: PartidaRepository): DeletePartidaUsercase {
        return DeletePartidaUsercase(partidaRepository)
    }

    @Provides
    fun providesListaPartidaUsercase(partidaRepository: PartidaRepository): ListPartidaUsercase {
        return ListPartidaUsercase(partidaRepository)
    }

    @Provides
    fun providesListaPartidaByDataUsercase(partidaRepository: PartidaRepository): ListaPartidaByDataUsercase {
        return ListaPartidaByDataUsercase(partidaRepository)
    }

    @Provides
    fun providesRegisterPartidaUsercase(partidaRepository: PartidaRepository): RegisterPartidaUsercase {
        return RegisterPartidaUsercase(partidaRepository)
    }

    @Provides
    fun providesUpdatePartidaUsercase(partidaRepository: PartidaRepository): UpdatePartidaUserCase {
        return UpdatePartidaUserCase(partidaRepository)
    }



}