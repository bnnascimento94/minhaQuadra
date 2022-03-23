package com.example.minhaquadra.data.model

import kotlin.String

data class Jogador(
    val uidJogador: String?,
    val nome: String?,
    val cpf: String?,
    val uidEquipe: String?
){
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