package com.example.minhaquadra.presentation.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Jogador
import com.example.minhaquadra.data.util.ImageSaver
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentCadastrarTimeBinding
import com.example.minhaquadra.presentation.home.adapter.JogadorAdapter
import com.example.minhaquadra.presentation.home.adapter.PartidasAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CadastrarTimeFragment : Fragment() {

    lateinit var binding: FragmentCadastrarTimeBinding

    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var jogadorAdapter: JogadorAdapter

    @Inject
    lateinit var preferencias: Preferencias

    private var resultadoTirarFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                binding?.imgEquipe?.setImageBitmap(viewModel?.rotacionarImagem())
            } catch (e: Exception) {
                Toast.makeText(requireContext(), " $e", Toast.LENGTH_LONG)
            }
        }
    }

    companion object {
        private const val SOLICITAR_PERMISSAO = 1
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cadastrar_time, container, false)
        viewModel = (activity as HomeActivity).viewModel
        initRecyclerView()

        viewModel.getEquipe(preferencias.usuario!!)


        viewModel.equipeBuscada.observe(this, Observer { response ->
            when(response){
                is Resource.Success->{
                    viewModel.listarJogador(response.data?.uidEquipe!!)
                    viewModel.equipe = response.data
                    response.data?.let { equipe ->
                        binding.txtNomeTime.setText(equipe.nomeEquipe)
                        binding.switchSituacaoTime.isChecked = equipe.situacaoTime!!

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

        viewModel.jogadorListado.observe(requireActivity(), Observer { response ->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        jogadorAdapter.load(response.data)
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

        binding.adicionarFoto.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val checkCameraPermission =
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            val permissionRead = ContextCompat.checkSelfPermission(
                requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), SOLICITAR_PERMISSAO
                )
            } else if (checkCameraPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.CAMERA
                    ), SOLICITAR_PERMISSAO
                )
            } else if (permissionRead != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), SOLICITAR_PERMISSAO
                )
            } else {
                val intent = Intent()
                intent.action = MediaStore.ACTION_IMAGE_CAPTURE
                try {
                    viewModel?.filePhoto = ImageSaver.createImageFile(requireContext())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                        requireActivity(),
                        requireContext().packageName + ".provider", viewModel?.filePhoto!!
                    )
                )
                resultadoTirarFoto.launch(intent)
            }
        }

        viewModel.equipeRegistrada.observe(requireActivity(), Observer { response ->
            when(response){
                is Resource.Success->{
                    Snackbar.make(requireView(),"Cadastro Realizado", Snackbar.LENGTH_SHORT).show()
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

        viewModel.equipeAtualizada.observe(requireActivity(),Observer{ response ->
            when(response){
                is Resource.Success->{
                    Snackbar.make(requireView(),"Cadastro Atualizado", Snackbar.LENGTH_SHORT).show()
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

        binding.btnSalvar.setOnClickListener {
            val bitmap: Bitmap = binding.imgEquipe.drawToBitmap()
            val nomeEquipe: String = binding.txtNomeTime.text.toString()
            val responsavelTime:String? = preferencias.usuario
            val situacao:Boolean = binding.switchSituacaoTime.isChecked

            if(viewModel.filePhoto == null || nomeEquipe.isNullOrEmpty() || responsavelTime.isNullOrEmpty()){
                Snackbar.make(requireView(),"Não foi possível registrar pois há dados incompletos",Snackbar.LENGTH_SHORT).show()
            }else if(viewModel.equipe == null){
                viewModel.registrarEquipe(bitmap,nomeEquipe,responsavelTime!!,situacao)
            }else{
                viewModel.atualizarEquipe(bitmap, viewModel.equipe!!.uidEquipe!!,nomeEquipe,responsavelTime,situacao)
            }
        }

        binding.btnAdicionarJogador.setOnClickListener {
            if(viewModel.equipe != null){
                showDialogCadastrarJogador(viewModel.equipe!!)
            }else{
                Snackbar.make(requireView(),"Não foi possível adicionar jogador, pois a equipe não foi registrada", Snackbar.LENGTH_SHORT).show()
            }
        }




        return binding.root
    }

    private fun showProgressBar(){
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progress.visibility = View.GONE
    }

    private fun initRecyclerView(){
        binding.rv.apply {
            adapter = jogadorAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        jogadorAdapter.setOnItemClickListener { jogador ->
            if(viewModel.equipe != null){
                showDialogCadastrarJogador(viewModel.equipe!!,jogador)
            }else{
                Snackbar.make(requireView(),"Não foi possível adicionar jogador, pois a equipe não foi registrada", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDialogCadastrarJogador(equipe:Equipe, jogador: Jogador? = null){
        val bottomSheetCadastroJogador = BottomSheetCadastroJogador(object:
            BottomSheetCadastroJogador.Callback{

            override fun onSalvar(nome: String, cpf: String, uidEquipe: String) {
                viewModel.registrarJogador(nome, cpf, uidEquipe)
                viewModel.jogadorRegistrado.observe(requireActivity(), Observer { response ->
                    when(response){
                        is Resource.Success->{
                            Snackbar.make(requireView(),"Cadastro Realizado", Snackbar.LENGTH_SHORT).show()
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

            override fun onAtualizar(jogador: Jogador) {
                viewModel.atualizarJogador(jogador)
            }

            override fun onDelete(uidJogador: String) {
                viewModel.deletarJogador(uidJogador)
            }


        })

        val bundle = Bundle()
        bundle.putString("uidEquipe", equipe.uidEquipe)
        bundle.putSerializable("jogador", jogador)
        bottomSheetCadastroJogador.setArguments(bundle)
        bottomSheetCadastroJogador.show(activity?.supportFragmentManager!!, "tag")

    }

}