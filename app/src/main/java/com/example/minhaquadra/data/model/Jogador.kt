package com.example.minhaquadra.data.model

import java.io.Serializable
import kotlin.String

data class Jogador(
    val uidJogador: String? = null,
    var nome: String? = null,
    var cpf: String? = null,
    val uidEquipe: String? = null
): Serializable{
    fun jogadorToHash(): Map<String, Any?>{

        val jogador = mutableMapOf<String, Any?>()

        if(uidJogador != null){
            jogador.put("uidJogador", uidJogador)
        }
        if(nome != null){
            jogador.put("nome", nome)
        }
        if(cpf != null){
            jogador.put("cpf", cpf)
        }
        if(uidEquipe != null){
            jogador.put("uidEquipe", uidEquipe)
        }


        return jogador

    }
}