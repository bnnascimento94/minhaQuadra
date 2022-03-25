package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository
import java.util.*

class ListaPartidaByEquipeUsercase(private val repository: PartidaRepository) {
    suspend fun execute(uidEquipe: String): Resource<List<Partida>>? = repository.getPartidasPorEquipe(uidEquipe)
}