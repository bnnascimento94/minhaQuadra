package com.example.minhaquadra.data.repository.partida

import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.repository.datasource.PartidaDataSource
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository
import java.util.*

class PartidaRepositoryImpl(private val partidaDataSource: PartidaDataSource): PartidaRepository {
    override suspend fun registerPartida(
        reservaQuadra: Boolean?,
        confronto: Boolean?,
        uidMandante: String?,
        uidAdversario: String?,
        dataPartida: Long?,
        horaPartida: Long?,
        duracaoPartida: String
    ): Resource<Boolean>? {
        return partidaDataSource.registerPartida(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida,horaPartida, duracaoPartida)
    }

    override suspend fun updatePartida(partida: Partida): Resource<Partida>? {
        return partidaDataSource.updatePartida(partida)
    }

    override suspend fun deletePartida(uidPartida: String): Resource<Boolean>? {
        return partidaDataSource.deletePartida(uidPartida)
    }

    override suspend fun getPartida(uidPartida: String): Resource<Partida>? {
        return partidaDataSource.getPartida(uidPartida)
    }

    override suspend fun getPartidas(): Resource<List<Partida>>? {
        return partidaDataSource.getPartidas()
    }

    override suspend fun getPartidasPorData(data: Date): Resource<List<Partida>>? {
        return partidaDataSource.getPartidasPorData(data)
    }

    override suspend fun getPartidasPorEquipe(uidEquipe: String): Resource<List<Partida>>? {
        return partidaDataSource.getPartidasPorEquipe(uidEquipe)
    }
}