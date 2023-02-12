package com.example.kotlinlogin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class GodzinyActivity: AppCompatActivity() {

    private val zaj = mutableListOf<Zadanie>()//lista obiewktow rodzaju zajecia
    private lateinit var adapter : ZadaniaAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_godziny)

        // Tworzymy nasz ZadaniaAdapter i wiążemy go z listView
        adapter = ZadaniaAdapter(this, zaj, this)
        var listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        // Wczytujemy zadania z bazy danych
        odswiezListeZadan2()

    }

    fun odswiezListeZadan2() {
        Log.d("odswiezListeZadan","ENTER")
        // Konstruujemy zapytanie do bazy danych
        val url = "http://10.0.2.2/androiddb/"
        val jsonObject = JSONObject()

        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query","SELECT * from `zajecia` where dzien='${DatyActivity.day}'")



        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url,jsonObject,
                { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("odswiezListeZadan","Response: $response")
                        // Usuwamy wszystkie zadania z naszej listy lokalnej
                        zaj.clear()
                        // Pobieramy listę zadań zwróconą z bazy danych
                        //val jsonZadania : JSONArray = response.getJSONArray("message")
                        val jsonZadania : JSONArray = JSONArray(response["message"].toString())
                        // Przechodzimy po otrzymanej liście
                        for (i in 0 until jsonZadania.length()) {
                            // Dla każdego elementu tworzymy obiekt
                            // klasy Zadanie i wstawiamy go do listy lokalnej
                            val id = jsonZadania.getJSONObject(i).getInt("id")
                            val rodzaj = jsonZadania.getJSONObject(i).getString("rodzaj")
                            val dzien = jsonZadania.getJSONObject(i).getString("dzien")
                            val godzina = jsonZadania.getJSONObject(i).getString("godzina")
                            val sala = jsonZadania.getJSONObject(i).getInt("sala")
                            val trener_id = jsonZadania.getJSONObject(i).getInt("trener_id")

                            zaj.add(Zadanie(id,rodzaj ,  godzina, sala, trener_id))
                        }
                        // Informujemy adapter o konieczności
                        // odświeżenia widoku
                        adapter.notifyDataSetChanged()
                    } catch (e:Exception){
                        Log.d("odswiezListeZadan","Exception: $e")
                    }

                }, {
                    // Error in request
                    Log.d("odswiezListeZadan","Volley error: $it")
                })

        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    fun rezerwujZadanie(position: Int) {//override nie pozebne??
        val url = "http://10.0.2.2/androiddb/"
        Log.d("rezerwujZadanie","ENTER")
        // Pobieramy kliknięte zadanie z listy
        val zadanie = adapter.getItem(position) as Zadanie

        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query",
            "INSERT INTO `wybrane_zajecia` ( user_id, zajecia_id) VALUES (1,'${zadanie.id}')")
        Log.d("siema7", "eniu7")
        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url,jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("rezerwujZadanie","Response: $response")
                        // Odświeżamy całą listę zadań
                        //odswiezListeZadan2()
                        Toast.makeText(this, "zarezerwowano!", Toast.LENGTH_LONG).show()
                    } catch (e:Exception){
                        Log.d("rezerwujZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("rezerwujZadaniee","Volley error: $it")
                })

        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }


    fun onClickPowrot(v: View) {
        startActivity(Intent(this, DatyActivity::class.java))
    }


}