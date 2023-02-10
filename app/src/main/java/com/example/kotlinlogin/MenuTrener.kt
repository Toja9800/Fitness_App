package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class MenuTrener : AppCompatActivity() {

    private val zaj = mutableListOf<Zajecia>()
    private lateinit var adapter : ZajeciaTrenerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_trener)

    }

    fun onClickDodaj(v: View) {
        Log.d("dodajZadanie","ENTER")

        var textEdit = findViewById<EditText>(R.id.TypZajec)
        val typZajec = textEdit.text.toString()

        var textEdit2 = findViewById<EditText>(R.id.TypZajec)
        val sala = textEdit2.text.toString()

        var textEdit3 = findViewById<EditText>(R.id.TypZajec)
        val termin = textEdit3.text.toString()


        val  user = MainActivity.username
        val  password = MainActivity.password
        val role = MainActivity.role
        val query = "INSERT INTO `zajecia` (rodzaj, sala, termin, trener_id) VALUES ('$typZajec', '$sala', '$termin','$role')"

        val url = "http://10.0.2.2/androiddb/"


        // Post parameters
        val jsonObject = JSONObject()
        jsonObject.put("username",user)
        jsonObject.put("password",password)
        jsonObject.put("email","")
        jsonObject.put("query",query)

        Log.d("fun onClickQuery:","jsonObject: $jsonObject")

        // Volley post request with parameters
        val requestPOST =
            JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener { response ->
                    try {

                        Log.d("dodajZadanie","Response: $response")
                        textEdit.setText("")
                        // Odświeżamy całą listę zadań
                        odswiezListeZadan()

                    } catch (e:Exception){
                        Log.d("dodajZadanie","Exception: $e")
                    }
                }, Response.ErrorListener{
                    // Error in request
                    Log.d("dodajZadanie","Volley error: $it")
                })
        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)
    }

    fun odswiezListeZadan() {
        Log.d("odswiezListeZadan","ENTER")
        // Konstruujemy zapytanie do bazy danych
        val jsonObject = JSONObject()
        jsonObject.put("username", MainActivity.username)
        jsonObject.put("password", MainActivity.password)
        jsonObject.put("email","")
        jsonObject.put("query","SELECT * from `zajecia`")

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
                            val rodzaj = jsonZadania.getJSONObject(i).getString("rodzaj")
                            val termin = jsonZadania.getJSONObject(i).getString("termin")
                            val sala = jsonZadania.getJSONObject(i).getInt("sala")
                            val trener_id = jsonZadania.getJSONObject(i).getInt("trener_id")
                            print(rodzaj)
                            print(termin)
                            print(sala)

                            zaj.add(Zajecia(rodzaj , termin, sala, trener_id))
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




    fun onClickLogout(v: View) {


        MainActivity.username = ""
        MainActivity.password = ""
        MainActivity.session = ""
        MainActivity.role = ""
        MainActivity.loggedin = false

        startActivity(Intent(this, MainActivity::class.java))
    }



}