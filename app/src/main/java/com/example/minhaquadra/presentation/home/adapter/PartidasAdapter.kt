package com.example.minhaquadra.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.databinding.HomeListItemBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PartidasAdapter(): RecyclerView.Adapter<PartidasAdapter.MenuViewHolder>() {

    var partidas: List<Partida>?
    var onItemClick: ((Partida) -> Unit)? = null

    init {
        partidas = mutableListOf()
    }

    fun load(partidas: List<Partida>?){
        this.partidas = partidas!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val homeListItemBinding: HomeListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_list_item, parent, false
        )
        return MenuViewHolder(homeListItemBinding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val partida = partidas!![position]
        val d = Date(partida.dataPartida!! * 1000)
        val f: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val dataFormatada = f.format(d)

        holder.homeListItemBinding.txtDataDoJogo.text = dataFormatada
        holder.homeListItemBinding.txtTimes.text = "${partida.mandante?.nomeEquipe} x ${partida.adversario?.nomeEquipe}"
    }

    override fun getItemCount(): Int {
       return partidas!!.size
    }


    inner class MenuViewHolder(homeListItemBinding: HomeListItemBinding) :
        RecyclerView.ViewHolder(homeListItemBinding.getRoot()) {
        val homeListItemBinding: HomeListItemBinding
        //o init comporta-se como o construtor no java, sempre que se inicializa o objeto a classe procura o init
        init {
            this.homeListItemBinding = homeListItemBinding
            itemView.setOnClickListener {
                onItemClick?.invoke(partidas!![adapterPosition])
            }
        }
    }

    fun setOnItemClickListener(listener: (Partida)-> Unit){
        onItemClick = listener
    }



}