package com.example.minhaquadra.data.model

data class Partida(
    val uidPartida: String?,
    val reservaQuadra: Boolean?,
    val confronto: Boolean?,
    val uidAdversario: String?,
    val dataPartida: String?,
    val duracaoPartida: String
)