package com.example.kotlinlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    companion object {
        var username = ""
        var password = ""
        var session = ""
        var loggedin = false
        lateinit var role: Any
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickSwitchToRegister(v: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

    fun onClickLogin(v: View) {

        val  user = findViewById<EditText>(R.id.loginUserEditText).text.toString()
        val  password = findViewById<EditText>(R.id.loginPasswordEditText).text.toString()

        val url = "http://10.0.2.2/androiddb/"


        val query = "SELECT role_id FROM `users` WHERE username = '${user}'"

        val jsonrole = JSONObject()
        jsonrole.put("username",user)
        jsonrole.put("password",password)
        jsonrole.put("email","")
        jsonrole.put("query",query)


        // Post parameters
        val jsonObject = JSONObject()
        jsonObject.put("username",user)
        jsonObject.put("password",password)
        jsonObject.put("email","")


        // Volley post request with parameters
        val requestrole = JsonObjectRequest(Request.Method.POST,url,jsonrole,
            Response.Listener { response ->
                // Process the json
                try {
                    val role = response["message"].toString()
                    MainActivity.role = role

                    }catch (e:Exception){
                    Log.d("fun onClickrole:","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun onClickQuery:","Volley error: $it")
            })

        VolleySingleton.getInstance(this).addToRequestQueue(requestrole)



        var intent = Intent(this, DatyActivity::class.java)

        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    if(MainActivity.role == """[{"role_id":"1"}]""") {
                        intent = Intent(this, DatyActivity::class.java)
                        Log.d("fun onClickrole", "odp: $role")
                        Log.d("fun onClickrole","odp: $response")
                    }
                    if(MainActivity.role == """[{"role_id":"2"}]""") {
                        intent = Intent(this, MainActivity3::class.java)
                        Log.d("fun onClickrole", "odp: $role")
                        Log.d("fun onClickrole","odp: $response")
                    }
                    processResponse(response, intent)
                    Log.d("fun onClickLogin:","Response: $response")
                }catch (e:Exception){
                    Log.d("fun onClickLogin:","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun onClickLogin:","Volley error: $it")
            })


        // zapamietajmy uzytego usera i haslo - na wypadek gdyby udalo sie zalogowac
        MainActivity.username = user
        MainActivity.password = password

        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

    }


    fun processResponse(response: JSONObject, intent: Intent) {
        if (response["success"]==1) {
            Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
            MainActivity.loggedin = true
            MainActivity.session = response["session"].toString()


            //jsonObject.put("query",query)

            startActivity(intent)
        }
        if (response["success"]==0) {
            MainActivity.loggedin = false
            MainActivity.session = ""
            MainActivity.username = ""
            MainActivity.password =""
            Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
        }

    }
}