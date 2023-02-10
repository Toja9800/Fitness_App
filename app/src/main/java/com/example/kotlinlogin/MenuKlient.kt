package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MenuKlient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_klient)
        val przywitanie: TextView = findViewById(R.id.textView4)
        przywitanie.setText("Jestes zalogowany/a jako: "+MainActivity.username+"")
    }


    fun onClickSwitchToDaty(v: View) {
        val intent = Intent(this, DatyActivity::class.java)
        startActivity(intent)
    }

    fun onClickSwitchToTwojeZaj(v: View) {
        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }

    fun onClickLogout(v: View) {


        MainActivity.username = ""
        MainActivity.password = ""
        MainActivity.session = ""
        MainActivity.role = ""
        MainActivity.loggedin = false

        startActivity(Intent(this, MainActivity::class.java))
    }





}