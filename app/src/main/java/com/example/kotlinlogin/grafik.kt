package com.example.kotlinlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import org.json.JSONObject

class grafik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grafik)
    }


//    fun rezerwuj(v: View) {
//        val zadanie = adapter.getItem(position) as Zadanie
//
//        val jsonObject = JSONObject()
//        jsonObject.put("username", MainActivity.username)
//        jsonObject.put("password", MainActivity.password)
//        jsonObject.put("query",
//            "UPDATE `zadania` SET zrobione=${if (.zrobione) 0 else 1} WHERE id=${.id}")
//    }
}