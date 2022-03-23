package com.example.minhaquadra.domain.usercases.jogador

import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class DeleteJogadorUsercase(private val repository: JogadorRepository) {
    suspend fun execute(uidJogador:String): Resource<Boolean>? = repository.deleteJogador(uidJogador)
}