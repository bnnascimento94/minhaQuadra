package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository

class RegisterUsercase(private val repository: PartidaRepository) {

    suspend fun execute(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: String?, duracaoPartida: String): Resource<Boolean>? = repository.registerPartida(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida)
}