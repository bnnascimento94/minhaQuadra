package com.example.minhaquadra.domain.usercases.equipe

import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.EquipeRepository

class DeleteEquipeUsercase(private val equipeRepository: EquipeRepository) {
    suspend fun execute(uid:String): Resource<Boolean>? = equipeRepository.deleteEquipe(uid)

}