package com.example.minhaquadra.data.model

import java.io.Serializable
import kotlin.String

data class Equipe(
    val uidEquipe: String?,
    val pathFoto: String?,
    val nomeEquipe: String?,
    val responsavelEquipe: String?,
    val situacaoTime: Boolean?,
    var donwloadUrl: String? = null,
): Serializable{

    fun equipeToHash(): Map<String, Any?>{
        val equipe = mutableMapOf<String, Any?>()

        if(uidEquipe != null){
            equipe.put("uidEquipe", uidEquipe)
        }
        if(pathFoto != null){
            equipe.put("pathFoto", uidEquipe)
        }
        if(nomeEquipe != null){
            equipe.put("nomeEquipe", nomeEquipe)
        }
        if(responsavelEquipe != null){
            equipe.put("responsavelEquipe", responsavelEquipe)
        }
        if(situacaoTime != null){
            equipe.put("situacaoTime", situacaoTime)
        }

        return equipe

    }
}