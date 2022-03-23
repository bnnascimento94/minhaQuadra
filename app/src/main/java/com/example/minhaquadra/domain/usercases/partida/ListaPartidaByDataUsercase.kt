package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository

class ListaPartidaByDataUsercase(private val repository: PartidaRepository) {
    suspend fun execute(data:String): Resource<List<Partida>>? = repository.getPartidasPorData(data)
}