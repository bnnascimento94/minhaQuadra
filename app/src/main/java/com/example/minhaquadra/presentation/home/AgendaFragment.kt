package com.example.minhaquadra.presentation.home

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minhaquadra.R
import com.example.minhaquadra.data.util.Preferencias
import com.example.minhaquadra.data.util.Resource
import com.example.minhaquadra.databinding.CalendarDayBinding
import com.example.minhaquadra.databinding.FragmentAgendaBinding
import com.example.minhaquadra.presentation.home.adapter.PartidasDoDiaAdapter
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarview.utils.Size
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AgendaFragment : Fragment() {


    private var selectedDate = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val dayFormatter = DateTimeFormatter.ofPattern("EEE")
    private val monthFormatter = DateTimeFormatter.ofPattern("MMM")
    private val fullDateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY")
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var binding: FragmentAgendaBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_agenda, container, false)
        viewModel = (activity as HomeActivity).viewModel
        initRecyclerView()

        buscarDiaPartida(Date())
        viewModel.partidaDataListada.observe(requireActivity(), androidx.lifecycle.Observer { response ->
            when(response){
                is Resource.Success->{
                    partidasDoDiaAdapter.load(response.data)
                    hideProgressBar()
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dm = DisplayMetrics()
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        binding.exSevenCalendar.apply {
            val dayWidth = dm.widthPixels / 5
            val dayHeight = (dayWidth * 1.25).toInt()
            daySize = Size(dayWidth, dayHeight)
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = CalendarDayBinding.bind(view)
            lateinit var day: CalendarDay

            init {
                view.setOnClickListener {
                    val firstDay = binding.exSevenCalendar.findFirstVisibleDay()
                    val lastDay = binding.exSevenCalendar.findLastVisibleDay()
                    if (firstDay == day) {
                        // If the first date on screen was clicked, we scroll to the date to ensure
                        // it is fully visible if it was partially off the screen when clicked.
                        binding.exSevenCalendar.smoothScrollToDate(day.date)
                    } else if (lastDay == day) {
                        // If the last date was clicked, we scroll to 4 days ago, this forces the
                        // clicked date to be fully visible if it was partially off the screen.
                        // We scroll to 4 days ago because we show max of five days on the screen
                        // so scrolling to 4 days ago brings the clicked date into full visibility
                        // at the end of the calendar view.
                        binding.exSevenCalendar.smoothScrollToDate(day.date.minusDays(4))
                    }

                    // Example: If you want the clicked date to always be centered on the screen,
                    // you would use: exSevenCalendar.smoothScrollToDate(day.date.minusDays(2))

                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.exSevenCalendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.exSevenCalendar.notifyDateChanged(it) }
                    }

                    var data: String? = fullDateFormatter.format(day.date)
                    buscarDiaPartida(dateFormat.parse(data))
                }
            }

            @RequiresApi(Build.VERSION_CODES.M)
            fun bind(day: CalendarDay) {
                this.day = day
                bind.exSevenDateText.text = dateFormatter.format(day.date)
                bind.exSevenDayText.text = dayFormatter.format(day.date)
                bind.exSevenMonthText.text = monthFormatter.format(day.date)

                bind.exSevenDateText.setTextColor(view.context.getColor(if (day.date == selectedDate) R.color.example_7_yellow else R.color.example_7_white))
                bind.exSevenSelectedView.isVisible = day.date == selectedDate
            }
        }

        binding.exSevenCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun bind(container: DayViewContainer, day: CalendarDay) = container.bind(day)
        }

        val currentMonth = YearMonth.now()
        // Value for firstDayOfWeek does not matter since inDates and outDates are not generated.
        binding.exSevenCalendar.setup(currentMonth, currentMonth.plusMonths(3), DayOfWeek.values().random())
        binding.exSevenCalendar.scrollToDate(LocalDate.now())
    }

    private fun buscarDiaPartida(date: Date){
        viewModel.listarPartidaPorData(date)
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