package com.example.minhaquadra.presentation.home.bottomSheet

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.minhaquadra.R
import com.example.minhaquadra.data.model.Equipe
import com.example.minhaquadra.data.model.Partida
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*
import android.widget.TimePicker.OnTimeChangedListener
import java.security.Timestamp


class BottomSheetCadastrarPartida(callback: Callback) : BottomSheetDialogFragment(){
    private var callback: Callback? = null
    var cal = Calendar.getInstance()

    init {
        this.callback = callback
    }


    interface Callback {
        fun onSalvar(reservaQuadra: Boolean?, confronto: Boolean?, uidMandante: String?, uidAdversario: String?, dataPartida: Long?,horaPartida: Long?, duracaoPartida: String)
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
        val txtPeriodo: AutoCompleteTextView = view.findViewById(R.id.periodo)
        val layoutAdversario: TextInputLayout = view.findViewById(R.id.txtAdversario)
        val txtAdversario: AutoCompleteTextView = view.findViewById(R.id.adversario)
        val txtDiaPartida: TextInputEditText = view.findViewById(R.id.txtDiaPartida)
        val txtHoraPartida: TextInputEditText = view.findViewById(R.id.txtHoraPartida)
        val chkBoxConfronto: CheckBox = view.findViewById(R.id.confronto)
        val chkBoxReserva: CheckBox = view.findViewById(R.id.reservaDeQuadra)
        val btnSalvar: MaterialButton = view.findViewById(R.id.btnSalvar)
        val btnDeletar: MaterialButton = view.findViewById(R.id.btnDelete)

        val periodos = mutableListOf<String>("30 Min","45 min", "1 hora", "2 Horas")
        val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.dropdown_item, periodos)
        txtPeriodo.setAdapter(adapter)

        val timeFormat = "hh:mm" // mention the format you need
        val horaFormato = SimpleDateFormat(timeFormat)

        val dateFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(dateFormat)

        if(bundle!!.containsKey("equipes")){
            equipes = bundle!!.getSerializable("equipes") as ArrayList<Equipe>?
            if(equipes != null && !equipes.isEmpty()){
                var adversarios = mutableListOf<String>()
                for(equipe in equipes!!){
                    adversarios.add(equipe.nomeEquipe!!)
                }
                val adapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), R.layout.dropdown_item, adversarios)
                txtAdversario.setAdapter(adapter)
            }
        }

        if(bundle.containsKey("uidEquipe")){
            uidEquipe = bundle.getString("uidEquipe")
        }

        if(bundle.containsKey("partida")){
            partida = bundle.getSerializable("partida") as Partida?
            partida?.let {
                txtHoraPartida.setText(horaFormato.format(Date(partida.horaPartida!!)))
                txtDiaPartida.setText(sdf.format(Date(partida.dataPartida!!)))
                partida.adversario?.let {
                    txtAdversario.setText(partida.adversario!!.nomeEquipe,false);
                }
                txtPeriodo.setText(partida!!.duracaoPartida,false);
                chkBoxConfronto.isChecked = partida?.confronto!!
                chkBoxReserva.isChecked = partida?.reservaQuadra!!
            }
        }

        if(partida == null){
            chkBoxReserva.isChecked = false
            chkBoxConfronto.isChecked = true
        }





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


        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                txtHoraPartida.setText(String.format("%d:%d", hourOfDay, minute))
            }
        }, hour, minute, false)

        txtHoraPartida.setOnClickListener {
            mTimePicker.show()
        }

        chkBoxReserva.setOnCheckedChangeListener { buttonView, isChecked ->
            chkBoxConfronto.isChecked = !isChecked
            txtAdversario.setText("")
            layoutAdversario.visibility = View.GONE
        }

        chkBoxConfronto.setOnCheckedChangeListener { buttonView, isChecked ->
           chkBoxReserva.isChecked = !isChecked
            layoutAdversario.visibility = View.VISIBLE
        }


        btnSalvar.setOnClickListener { view ->

            if(uidEquipe != null &&
                !txtDiaPartida.text!!.toString().equals("") &&
                !txtHoraPartida.text!!.toString().equals("") &&
                !txtPeriodo.text.toString().equals("")){


                var uidAdversario: String? = null
                if(!txtAdversario.text!!.toString().equals("")){
                    uidAdversario = equipes?.filter {equipe -> equipe.nomeEquipe.equals(txtAdversario.text.toString()) }!!.get(0).uidEquipe
                }


                if(partida != null){
                    val partida = Partida(
                        uidPartida = partida.uidPartida,
                        reservaQuadra = chkBoxReserva.isChecked,
                        confronto = chkBoxConfronto.isChecked,
                        uidMandante = uidEquipe,
                        uidAdversario = uidAdversario,
                        dataPartida = sdf.parse(txtDiaPartida.text.toString()).time,
                        horaPartida = horaFormato.parse(txtHoraPartida.text.toString()).time,
                        duracaoPartida = txtPeriodo.text.toString()
                    )
                    callback?.update(partida)
                    this.dismiss()
                }else{
                    callback?.onSalvar(chkBoxReserva.isChecked, chkBoxConfronto.isChecked,uidEquipe,uidAdversario,sdf.parse(txtDiaPartida.text.toString()).time,horaFormato.parse(txtHoraPartida.text.toString()).time,txtPeriodo.text.toString())
                    this.dismiss()
                }

            }else{
                Toast.makeText(requireContext(),"Não foi possível salvar, preencha os campos", Toast.LENGTH_LONG).show()
            }

        }

        btnDeletar.setOnClickListener { view->
          if(uidEquipe != null && partida != null){
              callback?.onDeletar(partida.uidPartida!!)
              this.dismiss()
          }else{
              Toast.makeText(requireContext(),"Não é possível deletar uma partida não criada", Toast.LENGTH_LONG).show()
          }
        }

        return view
    }




}

