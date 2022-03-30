package com.example.minhaquadra.domain.usercases.equipe

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.EquipeRepository

class ListEquipesUsercase(private val equipeRepository: EquipeRepository) {
    suspend fun execute(): Resource<ArrayList<Equipe>>? = equipeRepository.getEquipes()
}