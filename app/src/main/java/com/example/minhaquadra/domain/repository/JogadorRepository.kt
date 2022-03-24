package com.example.minhaquadra.domain.repository

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource

interface JogadorRepository {

    suspend fun registerJogador(nome: String, cpf: String, uidEquipe: String): Resource<Boolean>?

    suspend fun updateJogador(jogador: Jogador): Resource<Jogador>?

    suspend fun deleteJogador(uidJogador: String): Resource<Boolean>?

    suspend fun getJogador(uidJogador: String): Resource<Jogador>?

    suspend fun getJogadores(uidEquipe: String): Resource<List<Jogador>>?


}