package com.example.minhaquadra.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.minhaquadra.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetCadastrarPartida : BottomSheetDialogFragment() {

    private var callback: Callback? = null

    interface Callback {
        fun onSalvar()
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
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as Activity
        callback = try {
            activity as Callback
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() +
                        "must implement ExampleDialogListener"
            )
        }
    }

}