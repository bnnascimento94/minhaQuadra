package com.example.minhaquadra.presentation.home

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.ImageSaver
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.domain.usercases.equipe.*
import com.example.minhaquadra.domain.usercases.jogador.DeleteJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.ListJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.RegisterJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.UpdateJogadorUsercase
import com.example.minhaquadra.domain.usercases.partida.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
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
    val equipeListada: MutableLiveData<Resource<ArrayList<Equipe>>> = MutableLiveData()
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

    var filePhoto: File? = null
    var equipe: Equipe? = null
    var equipes: ArrayList<Equipe>? = null

    fun deletarEquipe(uidEquipe:String) = viewModelScope.launch(Dispatchers.IO){
       equipeDeletada.postValue(Resource.Loading())
       equipeDeletada.postValue(deleteEquipeUsercase.execute(uidEquipe)!!)
    }

    fun listarEquipes() = viewModelScope.launch(Dispatchers.IO) {
        equipeListada.postValue(Resource.Loading())
        equipeListada.postValue(listEquipesUsercase.execute())
    }

    fun getEquipe(uidUsuario:String) = viewModelScope.launch(Dispatchers.IO) {
        equipeBuscada.postValue(Resource.Loading())
        equipeBuscada.postValue(getEquipeUsercase.execute(uidUsuario))
    }

    fun registrarEquipe(foto: Bitmap, nomeEquipe: String,responsavelEquipe:String, situacaoTime: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        equipeRegistrada.postValue(Resource.Loading())
        equipeRegistrada.postValue(registerEquipeUsercase.execute(foto, nomeEquipe, responsavelEquipe, situacaoTime))
    }

    fun atualizarEquipe(foto: Bitmap?,pathFoto:String,uidEquipe: String, nomeEquipe: String,responsavelEquipe: String?, situacaoTime: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        equipeAtualizada.postValue(Resource.Loading())
        val equipe = Equipe(
            uidEquipe = uidEquipe,
            pathFoto = pathFoto,
            nomeEquipe = nomeEquipe,
            responsavelEquipe = responsavelEquipe,
            situacaoTime = situacaoTime
        )

        equipeAtualizada.postValue(updateEquipeUsercase.execute(equipe, foto))
    }


    fun deletarJogador(uidJogador:String) = viewModelScope.launch(Dispatchers.IO) {
        jogadorDeletado.postValue(Resource.Loading())
        jogadorDeletado.postValue(deleteJogadorUsercase.execute(uidJogador))
    }

    fun listarJogador(uidEquipe: String) = viewModelScope.launch(Dispatchers.IO) {
        jogadorListado.postValue(Resource.Loading())
        jogadorListado.postValue(listJogadorUsercase.execute(uidEquipe))
    }

    fun registrarJogador(nome: String, cpf: String, uidEquipe: String) = viewModelScope.launch(Dispatchers.IO) {
        jogadorRegistrado.postValue(Resource.Loading())
        jogadorListado.postValue(registerJogadorUsercase.execute(nome, cpf, uidEquipe))
    }

    fun atualizarJogador(jogador: Jogador) = viewModelScope.launch(Dispatchers.IO) {
        jogadorAtualizado.postValue(Resource.Loading())
        jogadorListado.postValue(updateJogadorUsercase.execute(jogador))
    }

    fun deletarPartida(uidPartida:String) = viewModelScope.launch(Dispatchers.IO) {
        partidaDeletada.postValue(Resource.Loading())
        partidaDeletada.postValue(deletePartidaUsercase.execute(uidPartida))
    }

    fun listarPartida() = viewModelScope.launch(Dispatchers.IO) {
        partidaListada.postValue(Resource.Loading())
        partidaListada.postValue(listPartidaUsercase.execute())
    }

    fun listarPartidaPorData(date: Date) = viewModelScope.launch(Dispatchers.IO) {
        partidaDataListada.postValue(Resource.Loading())
        partidaDataListada.postValue(listaPartidaByDataUsercase.execute(date))
    }

    fun listarPartidaPorEquipe(uidEquipe: String) = viewModelScope.launch(Dispatchers.IO) {
        partidaEquipeListada.postValue(Resource.Loading())
        partidaEquipeListada.postValue(listaPartidaByEquipeUsercase.execute(uidEquipe))
    }

    fun registrarPartida(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Long?, duracaoPartida: String) = viewModelScope.launch(Dispatchers.IO) {
        partidaRegistrada.postValue(Resource.Loading())
        partidaRegistrada.postValue(registerPartidaUsercase.execute(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida))
    }

    fun atualizarPartida(partida: Partida) = viewModelScope.launch(Dispatchers.IO) {
        partidaAtualizada.postValue(Resource.Loading())
        partidaAtualizada.postValue(updatePartidaUserCase.execute(partida))
    }

    fun rotacionarImagem() : Bitmap{
        return ImageSaver.rotateImage(filePhoto!!.absolutePath)
    }


}