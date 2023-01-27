package com.example.kotlinlogin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DatyActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daty)

        val date1: String = SimpleDateFormat("MMM dd yyyy",
        Locale.getDefault()).format(Date())

        val data1 = findViewById<TextView>(R.id.data1)

        data1.setText(date1)

        val date2: String = LocalDate.now().plusDays(1).toString()
        val data2 = findViewById<TextView>(R.id.data2)
        data2.setText(date2)
    }



}