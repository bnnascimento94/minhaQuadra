package com.example.minhaquadra

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaquadra.databinding.HomeListItemBinding

class HomeAdapter(menu: List<String>?): RecyclerView.Adapter<HomeAdapter.MenuViewHolder>() {

    var menu: List<String>
    var onItemClick: ((String) -> Unit)? = null

    init {
        this.menu = menu!!
    }



    inner class MenuViewHolder(homeListItemBinding: HomeListItemBinding) :
        RecyclerView.ViewHolder(homeListItemBinding.getRoot()) {
        val homeListItemBinding: HomeListItemBinding
        //o init comporta-se como o construtor no java, sempre que se inicializa o objeto a classe procura o init
        init {
            this.homeListItemBinding = homeListItemBinding
            itemView.setOnClickListener {
                onItemClick?.invoke(menu[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}