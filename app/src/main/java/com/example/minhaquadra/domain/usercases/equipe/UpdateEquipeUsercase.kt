package com.example.minhaquadra.domain.usercases.equipe

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.EquipeRepository

class UpdateEquipeUsercase(private val equipeRepository: EquipeRepository) {
    suspend fun execute(foto: Bitmap, nomeEquipe: Equipe, situacaoTime: kotlin.String): Resource<Equipe>? = equipeRepository.updateEquipe(foto, nomeEquipe, situacaoTime)
}