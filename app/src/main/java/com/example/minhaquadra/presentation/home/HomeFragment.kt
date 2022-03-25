package com.example.minhaquadra.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(){

    lateinit var binding: FragmentHomeBinding

    lateinit var callback: BottomSheetCadastrarPartida.Callback

    @Inject
    lateinit var preferencias: Preferencias

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private lateinit var viewModel: HomeViewModel


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
                val bottomSheetCadastrarPartida = BottomSheetCadastrarPartida(object:
                    BottomSheetCadastrarPartida.Callback{
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel = (activity as HomeActivity).viewModel

        viewModel.getEquipe(preferencias.usuario!!)


        viewModel.equipeBuscada.observe(this, Observer { response ->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { equipe ->
                       binding.txtNomeTime.text = equipe.nomeEquipe
                       binding.txtResponsavel.text = equipe.responsavelEquipe
                       binding.txtSituacao.text = if(equipe!!.situacaoTime!!) "Ativo" else "Inativo"
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let { errorMessage->
                        Toast.makeText(activity,"An error occured: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })

    }

    private fun initRecyclerView(){
        binding.rv.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(){

    }

    private fun hideProgressBar(){

    }


}