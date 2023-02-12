package com.example.kotlinlogin

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

class DatyActivity : AppCompatActivity() {

    companion object{
        lateinit var day: String
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daty)

//        val date1: String = SimpleDateFormat("MMM dd yyyy",
//        Locale.getDefault()).format(Date())

        // ustawiamy aktualną datę jako tekst pola tekstowego
        val date1: String = LocalDate.now().toString()
        val data1 = findViewById<TextView>(R.id.data1)
        data1.setText(date1)

        val date2: String = LocalDate.now().plusDays(1).toString()
        val data2 = findViewById<TextView>(R.id.data2)
        data2.setText(date2)

        val date3: String = LocalDate.now().plusDays(2).toString()
        val data3 = findViewById<TextView>(R.id.data3)
        data3.setText(date3)

        val date4: String = LocalDate.now().plusDays(3).toString()
        val data4 = findViewById<TextView>(R.id.data4)
        data4.setText(date4)

        val date5: String = LocalDate.now().plusDays(4).toString()
        val data5 = findViewById<TextView>(R.id.data5)
        data5.setText(date5)

        val date6: String = LocalDate.now().plusDays(5).toString()
        val data6 = findViewById<TextView>(R.id.data6)
        data6.setText(date6)

        val date7: String = LocalDate.now().plusDays(6).toString()
        val data7 = findViewById<TextView>(R.id.data7)
        data7.setText(date7)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun Przejdz(v: View) {
        val intent = Intent(this, GodzinyActivity::class.java)

        var date: String = LocalDate.now().dayOfWeek.toString()
        when(v.id) {
            R.id.button9 -> date = LocalDate.now().dayOfWeek.toString()
            R.id.button10 -> date = LocalDate.now().plusDays(1).dayOfWeek.toString()
            R.id.button11 -> date = LocalDate.now().plusDays(2).dayOfWeek.toString()
            R.id.button12 -> date = LocalDate.now().plusDays(3).dayOfWeek.toString()
            R.id.button13 -> date = LocalDate.now().plusDays(4).dayOfWeek.toString()
            R.id.button14 -> date = LocalDate.now().plusDays(5).dayOfWeek.toString()
            R.id.button15 -> date = LocalDate.now().plusDays(6).dayOfWeek.toString()
        }


        DatyActivity.day = date


        Log.d("v.id wyswietlam", "R.id.button:${R.id.button9},   odp: ${v.id}")
        //Log.d("day wyswietlam", "odp: $day")
        Log.d("Mainday wyswietlam", "odp: ${DatyActivity.day}")
        Log.d("date wyswietlam", "odp: $date")
        startActivity(intent)
    }

    fun onClickPowrot(v: View) {


        startActivity(Intent(this, MenuKlient::class.java))
    }


}