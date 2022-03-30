package com.example.minhaquadra.data.model

import java.io.Serializable
import kotlin.String

data class Partida(
    val uidPartida: String?,
    val reservaQuadra: Boolean?,
    val confronto: Boolean?,
    val uidMandante: String?,
    var mandante : Equipe? = null,
    val uidAdversario: String?,
    var adversario : Equipe? = null,
    val dataPartida: Long?,
    val duracaoPartida: String
): Serializable{
    fun partidaToHash(): Map<String, Any?>{

        val partida = mutableMapOf<String, Any?>()

        if(uidPartida != null){
            partida.put("uidPartida", uidPartida)
        }
        if(reservaQuadra != null){
            partida.put("reservaQuadra", reservaQuadra)
        }
        if(confronto != null){
            partida.put("confronto", confronto)
        }
        if(uidMandante != null){
            partida.put("uidMandante", uidMandante)
        }

        if(uidAdversario != null){
            partida.put("uidAdversario", uidAdversario)
        }

        if(dataPartida != null){
            partida.put("dataPartida", dataPartida)
        }

        if(duracaoPartida != null){
            partida.put("duracaoPartida", duracaoPartida)
        }

        return partida

    }

}