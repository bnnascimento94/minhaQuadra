package com.example.minhaquadra.domain.repository

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.Resource

interface JogadorRepository {

    fun registerJogador(nome: Equipe, cpf: kotlin.String): Resource<Boolean>?

    fun updateJogador(nome: Equipe, cpf: kotlin.String): Resource<Jogador>?

    fun deleteJogador(uidJogador: kotlin.String): Resource<Boolean>?

    fun getJogador(uidJogador: kotlin.String): Resource<Jogador>?

    fun getJogadores(uidEquipe: kotlin.String): Resource<List<Jogador>>?


}