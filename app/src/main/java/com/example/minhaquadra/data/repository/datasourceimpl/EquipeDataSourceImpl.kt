package com.example.minhaquadra.data.repository.datasourceimpl

import android.graphics.Bitmap
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.repository.datasource.EquipeDataSource
import com.example.minhaquadra.data.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class EquipeDataSourceImpl(private val database: FirebaseFirestore, private val firebaseStorage: FirebaseStorage): EquipeDataSource {

    override suspend fun registerEquipe(
        foto: Bitmap,
        nomeEquipe: String,
        responsavelEquipe: String,
        situacaoTime: Boolean
    ): Resource<Boolean>? {

        return try {
             val uidEquipe = UUID.randomUUID().toString()
            //TODO salvarfoto
            val reference = saveEquipePhoto(foto,uidEquipe)

            if(reference != null){
                val equipe = Equipe(
                    uidEquipe = uidEquipe,
                    pathFoto = reference,
                    nomeEquipe = nomeEquipe,
                    responsavelEquipe = responsavelEquipe,
                    situacaoTime = situacaoTime)
                database.collection("minhaQuadra").document("equipe").set(equipe.equipeToHash()).await()
                Resource.Success(true)
            }else{
                Resource.Error("Could not upload photo")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }


    }

    private suspend fun saveEquipePhoto(bitmap: Bitmap, uidEquipe:String): String?{
        val storageRef = firebaseStorage.getReference()
        val mountainImagesRef = storageRef.child("equipes/$uidEquipe.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = mountainImagesRef.putBytes(data).await()

        if(uploadTask.bytesTransferred > 0){
            return "equipes/$uidEquipe.jpg"
        }else{
            return null
        }
    }

    private suspend fun deletePhoto(referenceImage: String?){
        val storageRef = firebaseStorage.getReference()
        val desertRef = storageRef.child(referenceImage!!)
        desertRef.delete().await()
    }

    override suspend  fun updateEquipe(equipe: Equipe, bitmap: Bitmap?): Resource<Equipe>? {
        return try {
            val result = database.collection("minhaQuadra").whereEqualTo("uidEquipe",equipe.uidEquipe).get().await()
            if(!result.isEmpty){
                if(bitmap != null){
                    deletePhoto( "equipes/${equipe.pathFoto}.jpg")
                    saveEquipePhoto(bitmap,equipe.uidEquipe!!)
                    for (document in result.documents){
                        val uid = document.id
                        val docRef = database.collection("minhaQuadra").document(uid)
                        docRef.update(equipe.equipeToHash())
                    }
                }else{
                    for (document in result.documents){
                        val uid = document.id
                        val docRef = database.collection("minhaQuadra").document(uid)
                        docRef.update(equipe.equipeToHash())
                    }
                }
                Resource.Success(equipe)
            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteEquipe(uid: String): Resource<Boolean>? {
        return try {
            val result = database.collection("minhaQuadra").whereEqualTo("uidEquipe",uid).orderBy("dataPartida",
                Query.Direction.DESCENDING).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    deletePhoto("equipes/${document["pathFoto"].toString()}.jpg")
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

    override suspend fun getEquipe(uidUsuario: String): Resource<Equipe>? {
        return try {
            var equipe: Equipe? = null
            val result = database.collection("minhaQuadra")
                .whereEqualTo("responsavelEquipe",uidUsuario)
                .get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    equipe = document.toObject(Equipe::class.java)!!
                    val storageRef = firebaseStorage.getReference()
                    val ref = storageRef.child("equipes/${equipe.pathFoto!!}.jpg")
                    equipe.donwloadUrl = ref.downloadUrl.await().toString()
                    break
                }
                Resource.Success(equipe!!)
            }else{
                Resource.Error("No data")
            }
        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun getEquipes(): Resource<ArrayList<Equipe>>? {
        return try {
            var equipes = ArrayList<Equipe>()
            val result = database.collection("minhaQuadra")
                .whereNotEqualTo("uidEquipe",null).get().await()
            if(!result.isEmpty){
                for (document in result.documents){
                    equipes.add(document.toObject(Equipe::class.java)!!)
                }
                Resource.Success(equipes)

            }else{
                Resource.Error("No data")
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }
}