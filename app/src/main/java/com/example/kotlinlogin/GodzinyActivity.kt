package com.example.kotlinlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class GodzinyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_godziny)
    }


    fun Rezerwuj(v: View) {
        val button = findViewById<Button>(R.id.button1)
        //to tez w zaleznosci czy jest juz zarezerwowane czy nie
        Toast.makeText(this, "Zarezerwowane!", Toast.LENGTH_LONG).show()
        // zmienianie koloru + zmienianie tekstu w zaleznosci od tego czy zarezerwowane czy nie
        //button.setBackgroundColor()
        //button.setText("")
    }

}