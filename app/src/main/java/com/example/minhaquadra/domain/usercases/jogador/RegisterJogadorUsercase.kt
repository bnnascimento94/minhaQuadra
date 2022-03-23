package com.example.minhaquadra.domain.usercases.jogador

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class RegisterJogadorUsercase(private val repository: JogadorRepository) {
    suspend fun execute(nome: Equipe, cpf: kotlin.String): Resource<Boolean>? = repository.registerJogador(nome, cpf)
}