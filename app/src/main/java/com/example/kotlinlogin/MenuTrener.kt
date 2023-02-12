package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

class MenuTrener : AppCompatActivity() {

    private val zaj = mutableListOf<Zajecia>()//lista obiewktow rodzaju zajecia
    private lateinit var adapter : ZajeciaTrenerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_trener)


        // Tworzymy nasz ZadaniaAdapter i wiążemy go z listView
        adapter = ZajeciaTrenerAdapter(this, zaj, this)
        var listView = findViewById<ListView>(R.id.listView)
        listView.adapter = adapter

        // Wczytujemy zadania z bazy danych
        odswiezListeZadan()

    }

    fun onClickDodaj(v: View) {
        Log.d("dodajZadanie","ENTER")

        var textEdit = findViewById<EditText>(R.id.TypZajec)
        val typZajec = textEdit.text.toString()

        var textEdit2 = findViewById<EditText>(R.id.sala)
        val sala = textEdit2.text.toString()

        var textEdit3 = findViewById<EditText>(R.id.data)
        var dzien = textEdit3.text.toString()

        var textEdit4 = findViewById<EditText>(R.id.godzina)
        val godzina = textEdit4.text.toString()


        val  user = MainActivity.username
        val  password = MainActivity.password

        val query = "INSERT INTO `zajecia` (rodzaj, sala, dzien,godzina,trener_id) VALUES ('$typZajec', '$sala', '$dzien','$godzina','${MainActivity.id}')"

        val url = "http://10.0.2.2/androiddb/"


        // Post parameters
        val jsonObject = JSONObject()
        jsonObject.put("username",user)
        jsonObject.put("password",password)
        jsonObject.put("email","")
        jsonObject.put("query",query)

       // Log.d("fun onClickQuery:","jsonObject: $jsonObject")
        odswiezListeZadan()

        // Volley post request with parameters
        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {
                        odswiezListeZadan()

                        Log.d("dodajZadanie","Response: $response")
                        textEdit.setText("")
                        textEdit2.setText("")
                        textEdit3.setText("")
                        textEdit4.setText("")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()
                        Toast.makeText(this, "Dodano!", Toast.LENGTH_LONG).show()

                    } catch (e:Exception){
                        Log.d("dodajZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("dodajZadanie","Volley error: $it")
                })
        odswiezListeZadan()
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

    }

    fun odswiezListeZadan() {
        Log.d("odswiezListeZadan","ENTER")
        // Konstruujemy zapytanie do bazy danych
        val url = "http://10.0.2.2/androiddb/"
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query","SELECT id, rodzaj, dzien,godzina, sala, trener_id from `zajecia`")

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
            "DELETE from `zajecia` WHERE id=${zadanie.id}")

        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url,jsonObject,
                Response.Listener { response ->
                    try {
                        // Obsługa odpowiedzi z bazy danych
                        Log.d("usunZadanie","Response: $response")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()
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
        odswiezListeZadan()

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