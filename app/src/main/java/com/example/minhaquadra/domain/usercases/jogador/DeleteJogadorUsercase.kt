package com.example.minhaquadra.domain.usercases.jogador

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class DeleteJogadorUsercase(private val repository: JogadorRepository) {
    suspend fun execute(jogador: Jogador): Resource<Boolean>? = repository.deleteJogador(jogador)
}