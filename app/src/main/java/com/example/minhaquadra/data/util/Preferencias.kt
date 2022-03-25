package com.example.minhaquadra.data.util

import android.content.Context
import android.content.SharedPreferences
import com.example.minhaquadra.data.model.User

class Preferencias(contexto: Context) {
    private val preferences: SharedPreferences
    private val NOME_ARQUIVO = "dadosUsuario"
    private val MODE = 0
    private val editor: SharedPreferences.Editor
    private val USUARIO_UID = "ID"

    fun salvarDados(user: User) {
        editor.putString(USUARIO_UID, user.uid)
        editor.commit()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }

    val usuario: String?
        get() = preferences.getString(USUARIO_UID, null)


    init {
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE)
        editor = preferences.edit()
    }
}