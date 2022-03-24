package com.example.minhaquadra.data.repository.jogador

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.repository.datasource.JogadorDataSource
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class JogadorRepositoryImpl(private val jogadorDataSource: JogadorDataSource): JogadorRepository {

    override suspend fun registerJogador(
        nome: String,
        cpf: String,
        uidEquipe: String
    ): Resource<Boolean>? {
        return jogadorDataSource.registerJogador(nome, cpf, uidEquipe)
    }

    override suspend fun updateJogador(jogador: Jogador): Resource<Jogador>? {
        return jogadorDataSource.updateJogador(jogador)
    }

    override suspend fun deleteJogador(uidJogador: String): Resource<Boolean>? {
        return jogadorDataSource.deleteJogador(uidJogador)
    }

    override suspend fun getJogador(uidJogador: String): Resource<Jogador>? {
        return jogadorDataSource.getJogador(uidJogador)
    }

    override suspend fun getJogadores(uidEquipe: String): Resource<List<Jogador>>? {
        return jogadorDataSource.getJogadores(uidEquipe)
    }

}