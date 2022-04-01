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
import com.bumptech.glide.Glide
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentHomeBinding
import com.example.minhaquadra.presentation.home.adapter.PartidasAdapter
import com.example.minhaquadra.presentation.home.bottomSheet.BottomSheetCadastrarPartida
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(){

    lateinit var binding: FragmentHomeBinding

    lateinit var callback: BottomSheetCadastrarPartida.Callback

    @Inject
    lateinit var preferencias: Preferencias

    @Inject
    lateinit var partidasAdapter: PartidasAdapter

    private lateinit var viewModel: HomeViewModel

    private lateinit var equipe : Equipe


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
                viewModel.listarEquipes()
                viewModel.equipeListada.observe(this, Observer { response ->
                    when(response){
                        is Resource.Success->{
                            viewModel.equipes = response.data
                            showDialogCadastrarPartida(response.data!!)
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


            }catch (e: Exception){
                Log.i("teste", e.message!!)
            }

        }

        return binding.root
    }

    private fun showDialogCadastrarPartida(equipes:ArrayList<Equipe>, partida: Partida? = null){
        val bottomSheetCadastrarPartida = BottomSheetCadastrarPartida(object: BottomSheetCadastrarPartida.Callback{

            override fun onSalvar(
                reservaQuadra: Boolean?,
                confronto: Boolean?,
                uidMandante: String?,
                uidAdversario: String?,
                dataPartida: Long?,
                duracaoPartida: String
            ) {
                viewModel.registrarPartida(reservaQuadra, confronto, uidMandante, uidAdversario, dataPartida, duracaoPartida)
                viewModel.partidaRegistrada.observe(requireActivity(), Observer { response ->
                    when(response){
                        is Resource.Success->{
                            Snackbar.make(requireView(),"Partida Registrada", Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Error->{
                            hideProgressBar()
                            Snackbar.make(requireView(),response.message.toString(), Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Loading->{
                            showProgressBar()
                        }
                    }
                })

            }

            override fun update(partida: Partida) {
                viewModel.atualizarPartida(partida)
                viewModel.partidaAtualizada.observe(requireActivity(), Observer { response ->
                    when(response){
                        is Resource.Success->{
                            Snackbar.make(requireView(),"Partida Atualizada", Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Error->{
                            hideProgressBar()
                            Snackbar.make(requireView(),response.message.toString(), Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Loading->{
                            showProgressBar()
                        }
                    }
                })
            }


            override fun onDeletar(uidEquipe: String) {
                viewModel.deletarEquipe(uidEquipe)
                viewModel.equipeDeletada.observe(requireActivity(), Observer { response ->
                    when(response){
                        is Resource.Success->{
                            Snackbar.make(requireView(),"Equipe Deletada", Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Error->{
                            hideProgressBar()
                            Snackbar.make(requireView(),response.message.toString(), Snackbar.LENGTH_LONG).show()
                        }
                        is Resource.Loading->{
                            showProgressBar()
                        }
                    }
                })
            }


        })

        val bundle = Bundle()
        bundle.putSerializable("equipes",equipes)
        bundle.putSerializable("partida", partida)
        bundle.putString("uidEquipe", equipe.uidEquipe)
        bottomSheetCadastrarPartida.setArguments(bundle)

        bottomSheetCadastrarPartida.show(activity?.supportFragmentManager!!, "tag")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        viewModel = (activity as HomeActivity).viewModel

        viewModel.getEquipe(preferencias.usuario!!)


        viewModel.equipeBuscada.observe(this, Observer { response ->
            when(response){
                is Resource.Success->{
                    viewModel.equipe = response.data
                    viewModel.listarPartidaPorEquipe(response.data?.uidEquipe!!)
                    this.equipe = response.data
                    response.data?.let { equipe ->
                       binding.txtNomeTime.text = equipe.nomeEquipe
                       binding.txtSituacao.text = if(equipe!!.situacaoTime!!) "Ativo" else "Inativo"
                        Glide
                            .with(requireContext())
                            .load(equipe.donwloadUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_no_itens)
                            .into(binding.imgProfile);
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

        viewModel.partidaEquipeListada.observe(this, Observer { response ->
            when(response){
                is Resource.Success->{
                    partidasAdapter.load(response.data)
                }
                is Resource.Error->{
                    hideProgressBar()

                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })



    }

    private fun initRecyclerView(){
        binding.rv.apply {
            adapter = partidasAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        partidasAdapter.setOnItemClickListener { partida ->
            if(viewModel.equipes != null){
                showDialogCadastrarPartida(viewModel.equipes!!, partida)
            }
        }

    }

    private fun showProgressBar(){
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progress.visibility = View.GONE
    }


}