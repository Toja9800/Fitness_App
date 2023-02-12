package com.example.kotlinlogin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class KlientZajecia : AppCompatActivity() {

    private val zaj = mutableListOf<Zajecia>()//lista obiewktow rodzaju zajecia
    private lateinit var adapter : KlientZajeciaAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_klient_zajecia)


        // Tworzymy nasz ZadaniaAdapter i wiążemy go z listView
        adapter = KlientZajeciaAdapter(this, zaj, this)
        var listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        // Wczytujemy zadania z bazy danych
       odswiezListeZadan2()



    }



    fun odswiezListeZadan2() {
        val url = "http://10.0.2.2/androiddb/"
        Log.d("odswiezListeZadan","ENTER")
        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query","SELECT zajecia.id, zajecia.rodzaj, zajecia.dzien,zajecia.dzien,zajecia.godzina, zajecia.sala," +
               " zajecia.trener_id ,wybrane_zajecia.id from `zajecia` join `wybrane_zajecia` on wybrane_zajecia.zajecia_id=zajecia.id " +
               "where wybrane_zajecia.user_id=1 ")



        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url,jsonObject,
                Response.Listener { response ->
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
                            print(id)
                            print(rodzaj)
                            print(dzien)
                            print(godzina)

                            print(sala)
                            print(trener_id)

                            zaj.add(Zajecia(id,rodzaj , dzien,godzina, sala, trener_id))
                        }
                        // Informujemy adapter o konieczności
                        // odświeżenia widoku
                        adapter.notifyDataSetChanged()
                    } catch (e:Exception){
                        Log.d("odswiezListeZadan","Exception: $e")
                    }

                }, Response.ErrorListener{
                    // Error in request
                    Log.d("odswiezListeZadan","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    fun usunZadanie(position: Int) {//override nie pozebne??
        val url = "http://10.0.2.2/androiddb/"
        Log.d("usunZadanie","ENTER")
        // Pobieramy kliknięte zadanie z listy
        val zadanie = adapter.getItem(position) as Zajecia

        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query",
            "DELETE from `wybrane_zajecia` WHERE id=${zadanie.id} ")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url,jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("usunZadanie","Response: $response")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan2()
                        Toast.makeText(this, "usunieto!", Toast.LENGTH_LONG).show()
                    } catch (e:Exception){
                        Log.d("usunZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("usunZadanie","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }
    fun Odswiez(v:View){
        odswiezListeZadan2()

    }


    fun onClickPowrot(v: View) {


        startActivity(Intent(this, MenuKlient::class.java))
    }


}