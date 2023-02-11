package com.example.kotlinlogin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class Testowe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testowe)
    }





    fun usunZadanie(v: View) {


            val  user = MainActivity.username
            val  password = MainActivity.password
            val  id2 = findViewById<EditText>(R.id.editTextTextPersonName2).text.toString()
            val  query = "DELETE from `zajecia` WHERE id=${id2}"

            val url = "http://10.0.2.2/androiddb/"


            // Post parameters
            val jsonObject = JSONObject()
            jsonObject.put("username",user)
            jsonObject.put("password",password)
            jsonObject.put("email","")
            jsonObject.put("query",query)

            Log.d("fun onClickQuery:","jsonObject: $jsonObject")

            // Volley post request with parameters
            val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
                Response.Listener { response ->
                    // Process the json
                    try {

                        Log.d("fun usunZadanie:","Response: $response")

                        printResult(response)
                    }catch (e:Exception){
                        Log.d("fun  usunZadanie:","Exception: $e")
                    }

                }, Response.ErrorListener{
                    // Error in request
                    Log.d("fun  usunZadaniefrom `zajecia:","Volley error: $it")
                })

            VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

        }

        fun printResult(result : JSONObject) {
            findViewById<TextView>(R.id.textView9).text = result["message"].toString()
        }
}