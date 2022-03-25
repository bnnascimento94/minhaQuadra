package com.example.minhaquadra.presentation.home

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.usercases.equipe.*
import com.example.minhaquadra.domain.usercases.jogador.DeleteJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.ListJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.RegisterJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.UpdateJogadorUsercase
import com.example.minhaquadra.domain.usercases.partida.*
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    private val deleteEquipeUsercase: DeleteEquipeUsercase,
    private val listEquipesUsercase: ListEquipesUsercase,
    private val getEquipeUsercase: GetEquipeUsercase,
    private val registerEquipeUsercase: RegisterEquipeUsercase,
    private val updateEquipeUsercase: UpdateEquipeUsercase,
    private val deleteJogadorUsercase: DeleteJogadorUsercase,
    private val listJogadorUsercase: ListJogadorUsercase,
    private val registerJogadorUsercase: RegisterJogadorUsercase,
    private val updateJogadorUsercase: UpdateJogadorUsercase,
    private val deletePartidaUsercase: DeletePartidaUsercase,
    private val listPartidaUsercase: ListPartidaUsercase,
    private val listaPartidaByEquipeUsercase: ListaPartidaByEquipeUsercase,
    private val listaPartidaByDataUsercase: ListaPartidaByDataUsercase,
    private val registerPartidaUsercase: RegisterPartidaUsercase,
    private val updatePartidaUserCase: UpdatePartidaUserCase
): ViewModel() {

    val equipeBuscada: MutableLiveData<Resource<Equipe>> = MutableLiveData()
    val equipeDeletada: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val equipeListada: MutableLiveData<Resource<List<Equipe>>> = MutableLiveData()
    val equipeRegistrada: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val equipeAtualizada: MutableLiveData<Resource<Equipe>> = MutableLiveData()

    val jogadorDeletado: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val jogadorListado: MutableLiveData<Resource<List<Jogador>>> = MutableLiveData()
    val jogadorRegistrado: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val jogadorAtualizado: MutableLiveData<Resource<Jogador>> = MutableLiveData()

    val partidaDeletada: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val partidaListada: MutableLiveData<Resource<List<Partida>>> = MutableLiveData()
    val partidaDataListada: MutableLiveData<Resource<List<Partida>>> = MutableLiveData()
    val partidaEquipeListada: MutableLiveData<Resource<List<Partida>>> = MutableLiveData()
    val partidaRegistrada: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    val partidaAtualizada: MutableLiveData<Resource<Partida>> = MutableLiveData()

    fun deletarEquipe(uidEquipe:String) = viewModelScope.launch{
       equipeDeletada.postValue(deleteEquipeUsercase.execute(uidEquipe)!!)
    }

    fun listarEquipes() = viewModelScope.launch {
        equipeListada.postValue(listEquipesUsercase.execute())
    }

    fun getEquipe(uidUsuario:String) = viewModelScope.launch {
        equipeBuscada.postValue(getEquipeUsercase.execute(uidUsuario))
    }

    fun registrarEquipe(foto: Bitmap, nomeEquipe: String,responsavelEquipe:String, situacaoTime: Boolean) = viewModelScope.launch {
        equipeRegistrada.postValue(registerEquipeUsercase.execute(foto, nomeEquipe, responsavelEquipe, situacaoTime))
    }

    fun atualizarEquipe(foto: Bitmap,uidEquipe: String, nomeEquipe: String,responsavelEquipe: String?, situacaoTime: Boolean) = viewModelScope.launch {
        val equipe = Equipe(
            uidEquipe = uidEquipe,
            null,
            nomeEquipe = nomeEquipe,
            responsavelEquipe = responsavelEquipe,
            situacaoTime = situacaoTime
        )

        equipeAtualizada.postValue(updateEquipeUsercase.execute(equipe, foto))
    }


    fun deletarJogador(uidJogador:String) = viewModelScope.launch {
        jogadorDeletado.postValue(deleteJogadorUsercase.execute(uidJogador))
    }

    fun listarJogador(uidEquipe: String) = viewModelScope.launch {
        jogadorListado.postValue(listJogadorUsercase.execute(uidEquipe))
    }

    fun registrarJogador(nome: String, cpf: String, uidEquipe: String) = viewModelScope.launch {
        jogadorRegistrado.postValue(registerJogadorUsercase.execute(nome, cpf, uidEquipe))
    }

    fun atualizarJogador(uidJogador: String?,nome: String, cpf: String, uidEquipe: String) = viewModelScope.launch {
        val jogador = Jogador(
            uidJogador = uidJogador,
            nome = nome,
            cpf = cpf,
            uidEquipe = uidEquipe
        )
        jogadorAtualizado.postValue(updateJogadorUsercase.execute(jogador))
    }

    fun deletarPartida(uidPartida:String) = viewModelScope.launch {
        partidaDeletada.postValue(deletePartidaUsercase.execute(uidPartida))
    }

    fun listarPartida() = viewModelScope.launch {
        partidaListada.postValue(listPartidaUsercase.execute())
    }

    fun listarPartidaPorData(date: Date) = viewModelScope.launch {
        partidaDataListada.postValue(listaPartidaByDataUsercase.execute(date))
    }

    fun listarPartidaPorEquipe(uidEquipe: String) = viewModelScope.launch {
        partidaEquipeListada.postValue(listaPartidaByEquipeUsercase.execute(uidEquipe))
    }

    fun registrarPartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Date?, duracaoPartida: String) = viewModelScope.launch {
        partidaRegistrada.postValue(registerPartidaUsercase.execute(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida))
    }

    fun atualizarPartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Date?, duracaoPartida: String) = viewModelScope.launch {
        val partida = Partida(
            uidPartida = null,
            reservaQuadra = reservaQuadra,
            confronto = confronto,
            uidMandante = uidMandante,
            uidAdversario = uidAdversario,
            dataPartida = dataPartida?.time,
            duracaoPartida = duracaoPartida
        )

        partidaAtualizada.postValue(updatePartidaUserCase.execute(partida))
    }


}