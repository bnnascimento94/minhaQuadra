package com.example.minhaquadra.presentation.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Jogador
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class BottomSheetCadastroJogador(callback: Callback) : BottomSheetDialogFragment() {

    private var callback: Callback? = null

    init {
        this.callback = callback
    }

    interface Callback {
        fun onSalvar(nome: String, cpf: String, uidEquipe: String)
        fun onAtualizar(jogador: Jogador)
        fun onDelete(jogador: Jogador)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.bottom_sheet_cadastro_jogador, container, false)
        val btnSalvar: MaterialButton = view.findViewById(R.id.btnSalvar)
        val btnDeletar: MaterialButton = view.findViewById(R.id.btnDelete)

        val txtNomeJogador: TextInputEditText = view.findViewById(R.id.txtNomeJogador)
        val txtCpf: TextInputEditText = view.findViewById(R.id.txtCpf)

        val bundle = arguments
        var uidEquipe: String? = null
        var jogador : Jogador? = null

        if(bundle!!.containsKey("uidEquipe")){
            uidEquipe = bundle!!.getString("uidEquipe")
        }

        if(bundle!!.containsKey("jogador")){
            jogador = bundle!!.getSerializable("jogador") as Jogador?
            txtNomeJogador.setText(jogador?.nome)
            txtCpf.setText(jogador?.cpf)
        }

        btnSalvar.setOnClickListener { view ->
            val nomeJogador: String = txtNomeJogador.text.toString()
            val cpf:String = txtCpf.text.toString()

            if(!nomeJogador.isNullOrEmpty() && !cpf.isNullOrEmpty()){
                if (jogador != null){
                    jogador?.nome = nomeJogador
                    jogador.cpf = cpf
                    callback?.onAtualizar(jogador)
                    this.dismiss()
                }else{
                    callback?.onSalvar(nomeJogador,cpf,uidEquipe!!)
                    this.dismiss()
                }
            }
        }

        btnDeletar.setOnClickListener {
            if (jogador != null){
                callback?.onDelete(jogador)
            }else{
                Toast.makeText(requireContext(),"Não é possível deletar jogador não cadastrado", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }



}