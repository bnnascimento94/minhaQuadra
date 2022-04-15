package com.example.minhaquadra.data.repository.datasourceimpl

import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.repository.datasource.PartidaDataSource
import com.example.minhaquadra.data.util.DateToTimeStamp
import com.example.minhaquadra.data.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAmount
import java.time.temporal.TemporalUnit
import java.util.*

class PartidaDataSourceImpl(private val database: FirebaseFirestore): PartidaDataSource {

    private val hourFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

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

            if(checarSeHaPartidaNoMesmoHorario(partida)){
                Resource.Error("Horários de Partidas Conflitantes")
            }else{
                database.collection("partida")
                    .document(uidPartida)
                    .set(partida.partidaToHash())
                    .await()
                Resource.Success(true)
            }

        }catch(e: Exception){
            Resource.Error(e.message)
        }
    }

    override suspend fun updatePartida(partida: Partida): Resource<Partida>? {
        return try {
            if(checarSeHaPartidaNoMesmoHorario(partida)){
                database.collection("partida")
                    .document(partida.uidPartida!!)
                    .update(partida.partidaToHash()).await()
                Resource.Success(partida)
            }else{
                Resource.Error("Horários de Partidas Conflitantes")
            }

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
            val dateFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(dateFormat)
            val stringDate = sdf.format(Date())
            val dateFormatado = sdf.parse(stringDate).time


            val result = database.collection("partida")
                .whereEqualTo("dataPartida",dateFormatado)
                .get().await()
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

    override suspend fun getPartidasPorData(data: Date): Resource<List<Partida>>? {
        return try {
            var partidas = mutableListOf<Partida>()
            val dateFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(dateFormat)
            val stringDate = sdf.format(data)
            val dateFormatado = sdf.parse(stringDate).time

            val result = database.collection("partida")
                .whereEqualTo("dataPartida",dateFormatado).get().await()
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
                Resource.Success(partidas)
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

                    var partida : Partida = document.toObject(Partida::class.java)!!
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

    suspend fun checarSeHaPartidaNoMesmoHorario(partida:Partida): Boolean{
        val hourFormat = "HH:mm:ss"
        val dateFormat = "dd/MM/yyyy" // mention the format you need
        val hourf = SimpleDateFormat(hourFormat)
        val sdf = SimpleDateFormat(dateFormat)
        val stringDate = sdf.format(Date(partida.dataPartida as Long))
        val dateFormatado = sdf.parse(stringDate).time
        var temPartidaNoMesmoHorario = false;


        val result = database.collection("partida")
            .whereEqualTo("dataPartida",dateFormatado)
            .get().await()

        if(!result.isEmpty){
            for (document in result.documents){
             val hora =   hourf.format(Date(document["horaPartida"] as Long))
             var horaPartidaSalvaInicio = LocalTime.parse(hora,DateTimeFormatter.ISO_TIME)
             var horaPartidaInicio = LocalTime.parse(hourf.format(Date(partida.horaPartida!!)),DateTimeFormatter.ISO_TIME)
             var horaPartidaSalvaFim: LocalTime? = null
             var horaPartidaFim : LocalTime? = null
                    when(partida.duracaoPartida!!){
                        "30 Min" ->{
                            horaPartidaFim = horaPartidaInicio.plus(30,ChronoUnit.MINUTES)
                            horaPartidaSalvaFim = horaPartidaSalvaInicio.plus(30,ChronoUnit.MINUTES)
                        }
                        "45 min" ->{
                            horaPartidaFim = horaPartidaInicio.plus(45,ChronoUnit.MINUTES)
                            horaPartidaSalvaFim = horaPartidaSalvaInicio.plus(45,ChronoUnit.MINUTES)
                        }
                        "1 hora" ->{
                            horaPartidaFim = horaPartidaInicio.plus(1,ChronoUnit.HOURS)
                            horaPartidaSalvaFim = horaPartidaSalvaInicio.plus(1,ChronoUnit.HOURS)
                        }
                        "2 Horas" ->{
                            horaPartidaFim = horaPartidaInicio.plus(1,ChronoUnit.HOURS)
                            horaPartidaSalvaFim = horaPartidaSalvaInicio.plus(2,ChronoUnit.HOURS)
                        }
                    }

                if(horaPartidaInicio.isAfter(horaPartidaSalvaInicio) ||
                    horaPartidaInicio.isBefore(horaPartidaSalvaFim) ||
                    horaPartidaFim!!.isAfter(horaPartidaSalvaInicio)){
                    temPartidaNoMesmoHorario = partida.uidPartida != null && !partida.uidPartida.equals(document["uidPartida"])
                }

                if (temPartidaNoMesmoHorario) break else continue

            }

            return temPartidaNoMesmoHorario

        }else{
            return false
        }
    }

}