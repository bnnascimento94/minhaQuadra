package com.example.minhaquadra.data.model

import java.io.Serializable
import kotlin.String

data class Equipe(
    val uidEquipe: String? = null,
    val pathFoto: String? = null,
    val nomeEquipe: String?= null,
    val responsavelEquipe: String? = null,
    val situacaoTime: Boolean? = null,
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