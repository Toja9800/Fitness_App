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
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

    }

    fun onClickSwitchToLogin(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    fun onClickRegister(v: View) {

        val  user = findViewById<EditText>(R.id.registerUserEditText).text.toString()

        val  email = findViewById<EditText>(R.id.registerEmailEditText).text.toString()
        val  password = findViewById<EditText>(R.id.registerPasswordlEditText).text.toString()
        val  password2 = findViewById<EditText>(R.id.registerConfirmPasswordlEditText).text.toString()
        //val role_id = 1

        if (!password.equals(password2)) {
            Toast.makeText(this, "Hasla nie pasuja!", Toast.LENGTH_LONG).show()
            return
        }

        val url = "http://10.0.2.2/androiddb/"


        // Post parameters
        val jsonObject = JSONObject()
        jsonObject.put("username",user)
        jsonObject.put("password",password)
        jsonObject.put("email",email)
        //jsonObject.put("role_id", role_id)


        // Volley post request with parameters
        val requestPOST = JsonObjectRequest(Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
                // Process the json
                try {
                    processResponse(response)
                    Log.d("fun onClickRegister:","Response: $response")
                }catch (e:Exception){
                    Log.d("fun onClickRegister:","Exception: $e")
                }

            }, Response.ErrorListener{
                // Error in request
                Log.d("fun onClickRegister:","Volley error: $it")
            })


        VolleySingleton.getInstance(this).addToRequestQueue(requestPOST)

    }

    fun processResponse(response: JSONObject) {
        if (response["success"]==1) {
        Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        }
        if (response["success"]==0) {
            Toast.makeText(this, response["message"].toString(), Toast.LENGTH_LONG).show()
        }
    }
}
