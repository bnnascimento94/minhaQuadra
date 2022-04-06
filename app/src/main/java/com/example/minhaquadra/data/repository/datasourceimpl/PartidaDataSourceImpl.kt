package com.example.minhaquadra.data.repository.datasourceimpl

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.repository.datasource.PartidaDataSource
import com.example.minhaquadra.data.util.DateToTimeStamp
import com.example.minhaquadra.data.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.util.*

class PartidaDataSourceImpl(private val database: FirebaseFirestore): PartidaDataSource {

    override suspend fun registerPartida(
        reservaQuadra: Boolean?,
        confronto: Boolean?,
        uidMandante: String?,
        uidAdversario: String?,
        dataPartida: Long?,
        horaPartida: Long?,
        duracaoPartida: String
    ): Resource<Boolean>? {

        return try {
            val uidPartida = UUID.randomUUID().toString()
            val partida = Partida(
                uidPartida = uidPartida,
                reservaQuadra = reservaQuadra,
                confronto = confronto,
                uidMandante = uidMandante,
                uidAdversario = uidAdversario,
                dataPartida = dataPartida!!,
                horaPartida = horaPartida!!,
                duracaoPartida = duracaoPartida
            )

            database.collection("partida")
                    .document(uidPartida)
                    .set(partida.partidaToHash())
                    .await()
            Resource.Success(true)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updatePartida(partida: Partida): Resource<Partida>? {
        return try {
            database.collection("partida")
            .document(partida.uidPartida!!)
            .update(partida.partidaToHash()).await()
            Resource.Success(partida)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deletePartida(uidPartida: String): Resource<Boolean>? {
        return try {
            database.collection("partida").document(uidPartida).delete().await()
            Resource.Success(true)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getPartida(uidPartida: String): Resource<Partida>? {
        return try {
            var partida:Partida? = null
            val result = database.collection("partida")
                .whereEqualTo("uidPartida",uidPartida)
                .orderBy("dataPartida",Query.Direction.DESCENDING)
                .get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    partida = document.toObject(Partida::class.java)
                    break;
                }
                Resource.Success(partida!!)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getPartidas(): Resource<List<Partida>>? {
        return try {
            var partidas = mutableListOf<Partida>()
            val result = database.collection("partida")
                .whereLessThanOrEqualTo("dataPartida",Date().time)
                .get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    partidas.add(document.toObject(Partida::class.java)!!)
                }
                Resource.Success(partidas)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getPartidasPorData(data: Date): Resource<List<Partida>>? {
        return try {
            var partidas = mutableListOf<Partida>()
            val result = database.collection("partida")
                .whereGreaterThanOrEqualTo("dataPartida",data.time)
                .whereLessThanOrEqualTo("dataPartida",data.time).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val mandante = database.collection("equipe")
                        .document(document["uidMandante"].toString())
                        .get().await().toObject(Equipe::class.java)

                    val adversario = database.collection("equipe")
                        .document(document["uidAdversario"].toString())
                        .get().await().toObject(Equipe::class.java)

                    var partida = document.toObject(Partida::class.java)!!
                    partida.adversario = adversario
                    partida.mandante = mandante

                    partidas.add(partida)
                }
                Resource.Success(partidas)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getPartidasPorEquipe(uidEquipe: String): Resource<List<Partida>>? {
        return try {
            var partidas = mutableListOf<Partida>()
            val result = database.collection("partida")
                .whereEqualTo("uidMandante",uidEquipe).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val mandante = database.collection("equipe")
                        .document(document["uidMandante"].toString())
                        .get().await().toObject(Equipe::class.java)

                    val adversario = database.collection("equipe")
                        .document(document["uidAdversario"].toString())
                        .get().await().toObject(Equipe::class.java)

                    var partida = document.toObject(Partida::class.java)!!
                    partida.adversario = adversario
                    partida.mandante = mandante

                    partidas.add(partida)
                }
                Resource.Success(partidas)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }
}