package com.example.minhaquadra.domain.usercases.jogador

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class ListJogadorUsercase(private val repository: JogadorRepository) {
    suspend fun execute(uidEquipe:String): Resource<List<Jogador>>? = repository.getJogadores(uidEquipe)
}