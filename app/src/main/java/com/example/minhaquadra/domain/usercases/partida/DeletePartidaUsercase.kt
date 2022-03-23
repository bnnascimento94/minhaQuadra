package com.example.minhaquadra.domain.usercases.partida

import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.PartidaRepository

class DeletePartidaUsercase(private val repository: PartidaRepository) {
    suspend fun execute(uidPartida:String): Resource<Boolean>? = repository.deletePartida(uidPartida)
}