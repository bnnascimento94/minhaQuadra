package com.example.minhaquadra.data.repository.datasource

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import java.util.*

interface PartidaDataSource {

    suspend fun registerPartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Long?, duracaoPartida: String ): Resource<Boolean>?

    suspend fun updatePartida(partida: Partida): Resource<Partida>?

    suspend fun deletePartida(uidPartida:String): Resource<Boolean>?

    suspend fun getPartida(uidPartida:String): Resource<Partida>?

    suspend fun getPartidas(): Resource<List<Partida>>?

    suspend fun getPartidasPorData(data:Date): Resource<List<Partida>>?

    suspend fun getPartidasPorEquipe(uidEquipe: String): Resource<List<Partida>>?

}