package com.example.minhaquadra.data.repository.datasourceimpl

import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.repository.datasource.JogadorDataSource
import com.example.minhaquadra.data.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class JogadorDataSourceImpl(private val database: FirebaseFirestore): JogadorDataSource {

    override suspend fun registerJogador(nome: String, cpf: String, uidEquipe: String): Resource<List<Jogador>>? {
        return try {
            val uidJogdor = UUID.randomUUID().toString();
            val jogador = Jogador(
                uidJogador= uidJogdor,
                nome = nome,
                cpf = cpf,
                uidEquipe = uidEquipe
            )
            database.collection("minhaQuadra")
                .document("jogador")
                .collection(uidEquipe)
                .document(uidJogdor)
                .set(jogador.jogadorToHash())

            return getJogadores(jogador.uidEquipe!!)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updateJogador(jogador: Jogador): Resource<List<Jogador>>? {
        return try {
            val result = database.collection("minhaQuadra")
                .document("jogador")
                .collection(jogador.uidEquipe!!)
                .whereEqualTo("uidJogador",jogador.uidJogador).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    database.collection("minhaQuadra")
                    .document("jogador")
                    .collection(jogador.uidEquipe!!)
                    .document(document.id).update(jogador.jogadorToHash()).await()
                }
                return getJogadores(jogador.uidEquipe!!)
            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteJogador(jogador: Jogador): Resource<Boolean>? {
        return try {
            database.collection("minhaQuadra")
                .document("jogador")
                .collection(jogador.uidEquipe!!)
                .document(jogador.uidJogador!!).delete().await()
            return Resource.Success(true)
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getJogador(uidJogador: String): Resource<Jogador>? {
        return try {
            var jogador: Jogador? = null

            val result = database
                .collection("minhaQuadra")
                .whereEqualTo("uidJogador",uidJogador).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    jogador = document.toObject(Jogador::class.java)
                    break;
                }
                Resource.Success(jogador!!)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getJogadores(uidEquipe: String): Resource<List<Jogador>>? {
        return try {
            var jogadores = mutableListOf<Jogador>()
            val result = database
                .collection("minhaQuadra")
                .document("jogador")
                .collection(uidEquipe)
                .get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    jogadores.add(document.toObject(Jogador::class.java)!!)
                }
                Resource.Success(jogadores!!)
            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }
}