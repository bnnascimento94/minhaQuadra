package com.example.minhaquadra.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhaquadra.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetCadastrarPartida(callback: Callback) : BottomSheetDialogFragment() {
    private var callback: Callback? = null

    init {
        this.callback = callback
    }


    interface Callback {
        fun onSalvar()
        fun onDeletar()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.bottom_sheet_cadastro_partida, container, false)
        val btnSalvar: MaterialButton = view.findViewById(R.id.btnSalvar)
        val btnDeletar: MaterialButton = view.findViewById(R.id.btnDelete)

        btnSalvar.setOnClickListener { view ->
            callback?.onSalvar()
        }

        btnDeletar.setOnClickListener { view->
            callback?.onDeletar()
        }
        return view
    }


}