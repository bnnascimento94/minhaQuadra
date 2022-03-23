package com.example.minhaquadra.domain.repository

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource

interface EquipeRepository {

    fun registerEquipe(foto:Bitmap, nomeEquipe: Equipe, situacaoTime: kotlin.String):Resource<Boolean>?

    fun updateEquipe(foto:Bitmap, nomeEquipe: Equipe, situacaoTime: kotlin.String): Resource<Equipe>?

    fun deleteEquipe(uid: kotlin.String): Resource<Boolean>?

    fun getEquipe(uidUsuario: kotlin.String): Resource<Equipe>?

    fun getEquipes(): Resource<List<Equipe>>?

}