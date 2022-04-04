package com.example.minhaquadra.domain.usercases.jogador

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.JogadorRepository

class RegisterJogadorUsercase(private val repository: JogadorRepository) {
    suspend fun execute(nome: String, cpf: String, uidEquipe: String): Resource<List<Jogador>>? = repository.registerJogador(nome, cpf, uidEquipe)
}