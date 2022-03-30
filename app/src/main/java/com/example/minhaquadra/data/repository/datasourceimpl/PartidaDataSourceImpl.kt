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
        duracaoPartida: String
    ): Resource<Boolean>? {

        return try {
            val partida = Partida(
                uidPartida = UUID.randomUUID().toString(),
                reservaQuadra = reservaQuadra,
                confronto = confronto,
                uidMandante = uidMandante,
                uidAdversario = uidAdversario,
                dataPartida = dataPartida!!,
                duracaoPartida = duracaoPartida
            )
            database.collection("minhaQuadra").document("partida").set(partida.partidaToHash()).await()
            Resource.Success(true)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updatePartida(partida: Partida): Resource<Partida>? {
        return try {
            val result = database.collection("minhaQuadra").whereEqualTo("uidPartida",partida.uidPartida).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val uid = document.id
                    val docRef = database.collection("minhaQuadra").document(uid)
                    docRef.update(partida.partidaToHash())
                }

                Resource.Success(partida)
            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deletePartida(uidPartida: String): Resource<Boolean>? {
        return try {
            val result = database.collection("minhaQuadra").whereEqualTo("uidPartida",uidPartida).orderBy("dataPartida",Query.Direction.DESCENDING).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val uid = document.id
                    val docRef = database.collection("minhaQuadra").document(uid)
                    docRef.delete()
                    break;
                }
                Resource.Success(true)

            }else{
                Resource.Error("No data")
            }
            Resource.Success(true)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getPartida(uidPartida: String): Resource<Partida>? {
        return try {
            var partida:Partida? = null
            val result = database.collection("minhaQuadra").whereEqualTo("uidPartida",uidPartida).orderBy("dataPartida",Query.Direction.DESCENDING).get().await()
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
            val result = database.collection("minhaQuadra")
                .whereGreaterThanOrEqualTo("dataPartida",Date().time)
                .whereLessThanOrEqualTo("dataPartida",Date().time).get().await()
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
            val result = database.collection("minhaQuadra")
                .whereGreaterThanOrEqualTo("dataPartida",data.time)
                .whereLessThanOrEqualTo("dataPartida",data.time).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val mandante = database.collection("minhaQuadra")
                        .whereEqualTo("uidMandante",document["uidMandante"])
                        .get().await().documents.get(0).toObject(Equipe::class.java)

                    val adversario = database.collection("minhaQuadra")
                        .whereEqualTo("uidAdversario",document["uidAdversario"])
                        .get().await().documents.get(0).toObject(Equipe::class.java)

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
            val result = database.collection("minhaQuadra")
                .whereEqualTo("uidMandante",uidEquipe).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    val mandante = database.collection("minhaQuadra")
                        .whereEqualTo("uidMandante",document["uidMandante"])
                        .get().await().documents.get(0).toObject(Equipe::class.java)

                    val adversario = database.collection("minhaQuadra")
                        .whereEqualTo("uidAdversario",document["uidAdversario"])
                        .get().await().documents.get(0).toObject(Equipe::class.java)

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