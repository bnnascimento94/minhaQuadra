package com.example.minhaquadra.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.minhaquadra.R
import com.example.minhaquadra.databinding.FragmentHomeBinding
import com.example.minhaquadra.databinding.FragmentLoginBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment





class HomeFragment : Fragment(){

    lateinit var binding: FragmentHomeBinding

    lateinit var callback: BottomSheetCadastrarPartida.Callback


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)



        binding.btnEditTime.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_cadastrarTimeFragment)
        }

        binding.btnAdicional.setOnClickListener {
            try {
                val bottomSheetCadastrarPartida = BottomSheetCadastrarPartida(object:BottomSheetCadastrarPartida.Callback{
                    override fun onSalvar() {
                        Toast.makeText(activity,"salvo", Toast.LENGTH_LONG).show()
                    }

                    override fun onDeletar() {
                        Toast.makeText(activity,"deletado", Toast.LENGTH_LONG).show()
                    }

                })
                bottomSheetCadastrarPartida.show(activity?.supportFragmentManager!!, "tag")
            }catch (e: Exception){
                Log.i("teste", e.message!!)
            }

        }

        return binding.root
    }


}