package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository

class UpdatePartidaUserCase(private val repository: PartidaRepository) {
    suspend fun execute(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: String?, duracaoPartida: String): Resource<Partida>? = repository.updatePartida(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida)
}