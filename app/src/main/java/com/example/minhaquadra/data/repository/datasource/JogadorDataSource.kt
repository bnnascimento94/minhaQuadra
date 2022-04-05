package com.example.minhaquadra.data.repository.datasource

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource

interface JogadorDataSource {

    suspend fun registerJogador(nome: String, cpf: String, uidEquipe: String): Resource<List<Jogador>>?

    suspend fun updateJogador(jogador: Jogador): Resource<List<Jogador>>?

    suspend fun deleteJogador(jogador: Jogador): Resource<Boolean>?

    suspend fun getJogador(uidJogador: String): Resource<Jogador>?

    suspend fun getJogadores(uidEquipe: String): Resource<List<Jogador>>?
}