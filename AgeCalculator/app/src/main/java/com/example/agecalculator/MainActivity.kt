package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {
    var validation: Int= 0
    lateinit var selected_date: Date
    lateinit var yourAge: String
    lateinit var date_formater: SimpleDateFormat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        date_formater = SimpleDateFormat("dd/MM/yyyy") // It will change the date into a string buffer in this pattern
        val today_date = date_formater.format(Date()) // format function took a date as a argument and Date() is function which returns today's date
        showing_today_date.setText(today_date)
        click_to_show_cal.setOnClickListener{
            Opendatepicker()
        }

        calculateBtn.setOnClickListener {
            showingAgeFunc()
        }

    }
    @Suppress("DEPRECATION")
    fun Opendatepicker() {
        validation = 1
        val cal = Calendar.getInstance() // Creating a calendar instance to open a calendar form your phone
        val date = cal.time
        val year = date.year
        val month = date.month
        val day = date.day
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { View, selectYear, selectMonth, selectDay ->
            selected_date = Date(selectYear - 1900, selectMonth, selectDay)
            val show_date = date_formater.format(selected_date)
            showing_selected_date.setText(show_date)
            this.yourAge = claculate(selectYear, selectMonth, selectDay)
            // calculate is a function which return a string which is shown as a string, top of the your age. created below
        }, year, month, day).show() // In this line the year, month and day are the same as line 33, 34 and 35
    }

    fun claculate(selected_year: Int, selected_month: Int, selected_day: Int): String {
        val date_today = LocalDate.now()
        val todayDay = date_today.dayOfMonth
        val todayMonth = date_today.monthValue
        val todayYear = date_today.year
        var age_year:Int
        var age_month:Int
        var age_day:Int

        //Logic to find age and after calculating returning the string
        if (todayYear > selected_year) {
            if ((todayMonth >= selected_month) && (todayDay >= selected_day)) {
                age_year = todayYear - selected_year
                age_month = todayMonth - (selected_month+1)
                age_day = todayDay - selected_day
            } else if (todayMonth < selected_month && todayDay < selected_day) {
                age_year = (todayYear-1) - selected_year
                age_month = ((todayMonth + 12)-1) - (selected_month+1)
                age_day =  (todayDay + 30) - selected_day
            } else if (todayMonth < selected_month) {
                age_year = (todayYear-1) - selected_year
                age_month = (todayMonth + 12) - (selected_month+1)
                age_day =  todayDay - selected_day
            } else if (todayDay < selected_day) {
                age_year = todayYear - selected_year
                age_month = (todayMonth -1) - (selected_month+1)
                age_day =  (todayDay + 30) - selected_day
            } else {
                return "You Entered Wrong Date"
            }
            return "$age_year Years, $age_month Months, $age_day Days"
        } else {
            return "You have not born yet"
        }
    }

    fun showingAgeFunc() {
        if(validation != 0) {
            showing_your_age.setText(yourAge) // Showing the age of person after clicking the calculate button
        } else {
            Toast.makeText(this, "Please select your date of birth first.", Toast.LENGTH_LONG).show()
        }

    }

}
