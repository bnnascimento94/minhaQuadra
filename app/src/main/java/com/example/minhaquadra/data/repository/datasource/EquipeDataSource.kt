package com.example.minhaquadra.data.repository.datasource

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.util.Resource

interface EquipeDataSource {

    suspend fun registerEquipe(foto: Bitmap, nomeEquipe: String,responsavelEquipe:String, situacaoTime: Boolean): Resource<Boolean>?

    suspend fun updateEquipe(equipe: Equipe, bitmap: Bitmap?): Resource<Equipe>?

    suspend fun deleteEquipe(uid:String): Resource<Boolean>?

    suspend fun getEquipe(uidUsuario: String): Resource<Equipe>?

    suspend fun getEquipes(): Resource<ArrayList<Equipe>>?
}