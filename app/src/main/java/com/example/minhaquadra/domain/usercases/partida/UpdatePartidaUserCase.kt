package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository

class UpdatePartidaUserCase(private val repository: PartidaRepository) {
    suspend fun execute(partida: Partida): Resource<Partida>? = repository.updatePartida(partida)
}