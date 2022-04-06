package com.example.minhaquadra.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.databinding.PartidasAgendadasListItemBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PartidasDoDiaAdapter(): RecyclerView.Adapter<PartidasDoDiaAdapter.PartidasDoDiaHolder>() {
    var partidas: List<Partida>?

    init {
        partidas = mutableListOf()
    }

    fun load(partidas: List<Partida>?){
        this.partidas = partidas!!
        this.notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartidasDoDiaHolder {
        val partidasAgendadasListItemBinding: PartidasAgendadasListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.partidas_agendadas_list_item, parent, false)
        return PartidasDoDiaHolder(partidasAgendadasListItemBinding)
    }

    override fun onBindViewHolder(holder: PartidasDoDiaHolder, position: Int) {
        val partida = partidas!![position]
        val d = Date(partida.dataPartida!!)
        val horaFormat: DateFormat = SimpleDateFormat("HH:mm")
        var h: String? = partida.horaPartida?.let {
            val date  = Date(partida.horaPartida!!)
            "Às "+horaFormat.format(date).toString()
        }
        if(h == null){
            h = ""
        }
        val f: DateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dataFormatada = f.format(d)
        var confronto: String? = "Reserva de Quadra"
        partida.adversario?.let {
            confronto = "${partida.mandante?.nomeEquipe} x ${partida.adversario?.nomeEquipe}"
        }

        holder.partidasAgendadasListItemBinding.txtMandante.text = partida.mandante?.nomeEquipe
        holder.partidasAgendadasListItemBinding.txtDataDoJogo.text = "$dataFormatada $h"
        holder.partidasAgendadasListItemBinding.txtTimes.text = confronto
    }

    override fun getItemCount(): Int {
        return partidas!!.size
    }



    inner class PartidasDoDiaHolder(partidasAgendadasListItemBinding: PartidasAgendadasListItemBinding) :
        RecyclerView.ViewHolder(partidasAgendadasListItemBinding.getRoot()) {
        val partidasAgendadasListItemBinding: PartidasAgendadasListItemBinding
        //o init comporta-se como o construtor no java, sempre que se inicializa o objeto a classe procura o init
        init {
            this.partidasAgendadasListItemBinding = partidasAgendadasListItemBinding
        }
    }





}