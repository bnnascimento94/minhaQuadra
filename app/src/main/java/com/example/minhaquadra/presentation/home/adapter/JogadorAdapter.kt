package com.example.minhaquadra.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.databinding.JogadorListItemBinding

class JogadorAdapter(): RecyclerView.Adapter<JogadorAdapter.JogadorViewHolder>() {

    var jogadores: List<Jogador>?
    var onItemClick: ((Jogador) -> Unit)? = null

    init {
        jogadores = mutableListOf()
    }

    fun load(jogadores: List<Jogador>?){
        this.jogadores = jogadores!!
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogadorViewHolder {
        val jogadorListItemBinding: JogadorListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.jogador_list_item, parent, false
        )
        return JogadorViewHolder(jogadorListItemBinding)
    }

    override fun onBindViewHolder(holder: JogadorViewHolder, position: Int) {
        val jogador = jogadores!![position]
        holder.jogadorListItemBinding.txtNomeJogador.text = jogador.nome
        holder.jogadorListItemBinding.txtCpf.text = jogador.cpf
    }

    override fun getItemCount(): Int {
        return jogadores!!.size
    }


    inner class JogadorViewHolder(jogadorListItemBinding: JogadorListItemBinding) :
        RecyclerView.ViewHolder(jogadorListItemBinding.getRoot()) {
        val jogadorListItemBinding: JogadorListItemBinding
        //o init comporta-se como o construtor no java, sempre que se inicializa o objeto a classe procura o init
        init {
            this.jogadorListItemBinding = jogadorListItemBinding
            itemView.setOnClickListener {
                onItemClick?.invoke(jogadores!![adapterPosition])
            }
        }
    }

    fun setOnItemClickListener(listener: (Jogador)-> Unit){
        onItemClick = listener
    }




}