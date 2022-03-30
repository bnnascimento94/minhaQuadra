package com.example.minhaquadra.data.repository.equipe

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.repository.datasource.EquipeDataSource
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.repository.EquipeRepository

class EquipeRepositoryImpl(private val equipeDataSource: EquipeDataSource): EquipeRepository {
    override suspend fun registerEquipe(
        foto: Bitmap,
        nomeEquipe: String,
        responsavelEquipe: String,
        situacaoTime: Boolean
    ): Resource<Boolean>? {
        return equipeDataSource.registerEquipe(foto, nomeEquipe, responsavelEquipe, situacaoTime)
    }

    override suspend fun updateEquipe(equipe: Equipe, bitmap: Bitmap?): Resource<Equipe>? {
        return equipeDataSource.updateEquipe(equipe, bitmap)
    }

    override suspend fun deleteEquipe(uid: String): Resource<Boolean>? {
        return equipeDataSource.deleteEquipe(uid)
    }

    override suspend fun getEquipe(uidUsuario: String): Resource<Equipe>? {
        return equipeDataSource.getEquipe(uidUsuario)
    }

    override suspend fun getEquipes(): Resource<ArrayList<Equipe>>? {
        return equipeDataSource.getEquipes()
    }


}