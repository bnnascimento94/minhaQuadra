package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository
import java.util.*

class RegisterPartidaUsercase(private val repository: PartidaRepository) {

    suspend fun execute(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Date?, duracaoPartida: String): Resource<Boolean>? = repository.registerPartida(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida)
}