package com.example.minhaquadra.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.databinding.JogadorListItemBinding

class JogadorAdapter(): RecyclerView.Adapter<JogadorAdapter.JogadorViewHolder>() {

    var onItemClick: ((Jogador) -> Unit)? = null

    private val callback = object: DiffUtil.ItemCallback<Jogador>(){
        override fun areItemsTheSame(oldItem: Jogador, newItem: Jogador): Boolean {
            return oldItem.uidJogador === newItem.uidJogador
        }

        override fun areContentsTheSame(oldItem: Jogador, newItem: Jogador): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogadorViewHolder {
        val jogadorListItemBinding: JogadorListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.jogador_list_item, parent, false
        )
        return JogadorViewHolder(jogadorListItemBinding)
    }

    override fun onBindViewHolder(holder: JogadorViewHolder, position: Int) {
        val jogador = differ.currentList[position]
        holder.jogadorListItemBinding.txtNomeJogador.text = jogador.nome
        holder.jogadorListItemBinding.txtCpf.text = jogador.cpf
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class JogadorViewHolder(jogadorListItemBinding: JogadorListItemBinding) :
        RecyclerView.ViewHolder(jogadorListItemBinding.getRoot()) {
        val jogadorListItemBinding: JogadorListItemBinding
        init {
            this.jogadorListItemBinding = jogadorListItemBinding
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[adapterPosition])
            }
        }
    }

    fun setOnItemClickListener(listener: (Jogador)-> Unit){
        onItemClick = listener
    }




}