package com.example.minhaquadra.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.FragmentMatchesBinding
import com.example.minhaquadra.presentation.home.adapter.PartidasAdapter
import com.example.minhaquadra.presentation.home.adapter.PartidasDoDiaAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MatchesFragment : Fragment() {

    lateinit var binding: FragmentMatchesBinding

    @Inject
    lateinit var partidasDoDiaAdapter: PartidasDoDiaAdapter

    @Inject
    lateinit var preferencias: Preferencias

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matches, container, false)
        viewModel = (activity as HomeActivity).viewModel

        initRecyclerView()

        viewModel.listarPartida()
        viewModel.partidaListada.observe(this, Observer { response ->
            when(response){
                is Resource.Success->{
                    partidasDoDiaAdapter.load(response.data)
                }
                is Resource.Error->{
                    hideProgressBar()

                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }
        })

        return binding.root
    }

    private fun initRecyclerView(){
        binding.rv.apply {
            adapter = partidasDoDiaAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(){
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progress.visibility = View.GONE
    }



}