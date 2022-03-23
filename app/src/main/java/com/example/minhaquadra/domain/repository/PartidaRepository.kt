package com.example.minhaquadra.domain.repository

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource

interface PartidaRepository {

    fun registerPartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: String?, duracaoPartida: String ): Resource<Boolean>?

    fun updatePartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: String?, duracaoPartida: String): Resource<Partida>?

    fun deletePartida(uidPartida:String): Resource<Boolean>?

    fun getPartida(uidPartida:String): Resource<Partida>?

    fun getPartidas(): Resource<List<Partida>>?

    fun getPartidasPorData(data:String): Resource<List<Partida>>?

}