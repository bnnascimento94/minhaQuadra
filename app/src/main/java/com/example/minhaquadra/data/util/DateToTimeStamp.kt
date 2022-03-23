package com.example.minhaquadra.data.util

import java.text.SimpleDateFormat
import java.util.*

class DateToTimeStamp {

    companion object{
        fun dateToTimeStamp(date: Date): Long{
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val dateFormated = dateFormat.parse(dateFormat.format(date))
            return dateFormated.time
        }
    }
}