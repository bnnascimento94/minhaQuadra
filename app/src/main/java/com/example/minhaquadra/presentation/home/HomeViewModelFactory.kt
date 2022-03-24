package com.example.minhaquadra.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minhaquadra.domain.usercases.equipe.DeleteEquipeUsercase
import com.example.minhaquadra.domain.usercases.equipe.ListEquipesUsercase
import com.example.minhaquadra.domain.usercases.equipe.RegisterEquipeUsercase
import com.example.minhaquadra.domain.usercases.equipe.UpdateEquipeUsercase
import com.example.minhaquadra.domain.usercases.jogador.DeleteJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.ListJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.RegisterJogadorUsercase
import com.example.minhaquadra.domain.usercases.jogador.UpdateJogadorUsercase
import com.example.minhaquadra.domain.usercases.partida.*

class HomeViewModelFactory(
    private val deleteEquipeUsercase: DeleteEquipeUsercase,
    private val listEquipesUsercase: ListEquipesUsercase,
    private val registerEquipeUsercase: RegisterEquipeUsercase,
    private val updateEquipeUsercase: UpdateEquipeUsercase,
    private val deleteJogadorUsercase: DeleteJogadorUsercase,
    private val listJogadorUsercase: ListJogadorUsercase,
    private val registerJogadorUsercase: RegisterJogadorUsercase,
    private val updateJogadorUsercase: UpdateJogadorUsercase,
    private val deletePartidaUsercase: DeletePartidaUsercase,
    private val listPartidaUsercase: ListPartidaUsercase,
    private val listaPartidaByDataUsercase: ListaPartidaByDataUsercase,
    private val registerPartidaUsercase: RegisterPartidaUsercase,
    private val updatePartidaUserCase: UpdatePartidaUserCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(
            deleteEquipeUsercase,
            listEquipesUsercase,
            registerEquipeUsercase,
            updateEquipeUsercase,
            deleteJogadorUsercase,
            listJogadorUsercase,
            registerJogadorUsercase,
            updateJogadorUsercase,
            deletePartidaUsercase,
            listPartidaUsercase,
            listaPartidaByDataUsercase,
            registerPartidaUsercase,
            updatePartidaUserCase) as T
    }
}