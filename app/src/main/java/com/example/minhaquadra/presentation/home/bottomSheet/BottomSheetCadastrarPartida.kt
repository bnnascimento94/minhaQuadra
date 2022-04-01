package com.example.minhaquadra.presentation.home.bottomSheet

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetCadastrarPartida(callback: Callback) : BottomSheetDialogFragment(){
    private var callback: Callback? = null
    var cal = Calendar.getInstance()

    init {
        this.callback = callback
    }


    interface Callback {
        fun onSalvar(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Long?, duracaoPartida: String)
        fun update(partida: Partida)
        fun onDeletar(uidEquipe: String)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.bottom_sheet_cadastro_partida, container, false)
        val bundle = arguments
        var equipes: ArrayList<Equipe>? = null
        var uidEquipe:String? = null
        var partida: Partida? = null

        val timeFormat = "HH:MM" // mention the format you need
        val horaFormato = SimpleDateFormat(timeFormat)

        val dateFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(dateFormat)

        if(bundle!!.containsKey("equipes")){
            equipes = bundle!!.getSerializable("equipes") as ArrayList<Equipe>?
        }

        if(bundle!!.containsKey("uidEquipe")){
            uidEquipe = bundle!!.getString("uidEquipe")
        }

        if(bundle!!.containsKey("partida")){
            partida = bundle!!.getSerializable("partida") as Partida?
        }


        val txtDiaPartida: TextInputEditText = view.findViewById(R.id.txtDiaPartida)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            txtDiaPartida.setText(sdf.format(cal.time))
        }

        txtDiaPartida.setOnClickListener {
            DatePickerDialog(requireActivity(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val txtHoraPartida: TextInputEditText = view.findViewById(R.id.txtHoraPartida)

        val horaSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            txtHoraPartida.setText(horaFormato.format(cal.time))

        }

        txtHoraPartida.setOnClickListener {
            DatePickerDialog(requireActivity(), horaSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        val btnSalvar: MaterialButton = view.findViewById(R.id.btnSalvar)
        val btnDeletar: MaterialButton = view.findViewById(R.id.btnDelete)

        val chkBoxConfronto: CheckBox = view.findViewById(R.id.confronto)
        val chkBoxReserva: CheckBox = view.findViewById(R.id.reservaDeQuadra)
        chkBoxReserva.isChecked = true
        chkBoxConfronto.isChecked = false

        chkBoxReserva.setOnCheckedChangeListener { buttonView, isChecked ->
            chkBoxConfronto.isChecked = !isChecked
        }

        chkBoxConfronto.setOnCheckedChangeListener { buttonView, isChecked ->
           chkBoxReserva.isChecked = !isChecked
        }



        val txtAdversario: AutoCompleteTextView = view.findViewById(R.id.adversario)
        if(equipes != null && !equipes.isEmpty()){
            var adversarios = mutableListOf<String>()
            for(equipe in equipes){
                adversarios.add(equipe.nomeEquipe!!)
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.dropdown_item, adversarios)
            txtAdversario.setAdapter(adapter)
        }


        val txtPeriodo: AutoCompleteTextView = view.findViewById(R.id.periodo)
        val periodos = mutableListOf<String>("30 Min","45 min", "1 hora", "2 Horas")
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.dropdown_item, periodos)
        txtPeriodo.setAdapter(adapter)

        btnSalvar.setOnClickListener { view ->

            if(!txtAdversario.text.toString().equals("") &&
                uidEquipe != null &&
                !txtDiaPartida.text!!.equals("")  &&
                !txtPeriodo.text.toString().equals("")){

                val contra = equipes?.filter {equipe -> equipe.equals(txtAdversario.text.toString()) }

                if(partida != null){
                    val partida = Partida(
                        uidPartida = partida.uidPartida,
                        reservaQuadra = chkBoxReserva.isChecked,
                        confronto = chkBoxConfronto.isChecked,
                        uidMandante = uidEquipe,
                        uidAdversario = contra?.get(0)?.uidEquipe,
                        dataPartida = sdf.parse(txtDiaPartida.text.toString()).time,
                        duracaoPartida = txtPeriodo.text.toString()
                    )
                    callback?.update(partida)
                }else{
                    callback?.onSalvar(chkBoxReserva.isChecked, chkBoxConfronto.isChecked,uidEquipe, contra?.get(0)?.uidEquipe,sdf.parse(txtDiaPartida.text.toString()).time,txtPeriodo.text.toString())
                }

            }else{
                Snackbar.make(requireView(),"Não foi possível salvar, preencha os campos", Snackbar.LENGTH_LONG).show()
            }

        }

        btnDeletar.setOnClickListener { view->
           if(uidEquipe != null && partida != null){
               callback?.onDeletar(uidEquipe)
           }else{
               Snackbar.make(requireView(),"Não é possível deletar uma partida não criada", Snackbar.LENGTH_LONG).show()
           }

        }
        return view
    }




}

