package com.example.minhaquadra.data.model

import java.io.Serializable
import kotlin.String

data class Partida(
    val uidPartida: String? = null,
    val reservaQuadra: Boolean? = null,
    val confronto: Boolean? = null,
    val uidMandante: String? = null,
    var mandante : Equipe? = null,
    val uidAdversario: String? = null,
    var adversario : Equipe? = null,
    val dataPartida: Long? = null,
    val horaPartida: Long? = null,
    val duracaoPartida: String? = null
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


        partida.put("uidAdversario", uidAdversario)


        if(dataPartida != null){
            partida.put("dataPartida", dataPartida)
        }

        if(horaPartida != null){
            partida.put("horaPartida", horaPartida)
        }

        if(duracaoPartida != null){
            partida.put("duracaoPartida", duracaoPartida)
        }

        return partida

    }

}