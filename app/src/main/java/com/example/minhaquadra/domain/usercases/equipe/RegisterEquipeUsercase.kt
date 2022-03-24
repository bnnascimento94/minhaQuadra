package com.example.minhaquadra.domain.usercases.equipe

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.EquipeRepository

class RegisterEquipeUsercase(private val equipeRepository: EquipeRepository) {
    suspend fun execute(foto: Bitmap, nomeEquipe: String,responsavelEquipe:String, situacaoTime: Boolean): Resource<Boolean>? = equipeRepository.registerEquipe(foto, nomeEquipe, responsavelEquipe,situacaoTime)
}