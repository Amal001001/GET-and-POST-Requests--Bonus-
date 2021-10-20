package com.example.getandpostrequestsbonus

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var et: EditText
    lateinit var button: Button
    lateinit var button2: Button
    lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et = findViewById(R.id.et)
        button = findViewById(R.id.button)
        button.setOnClickListener { addName() }

        tv = findViewById(R.id.tv)
        button2 = findViewById(R.id.button2)
        button2.setOnClickListener { getNames() }
    }

    fun addName() {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (et.text != null) {
            // Hide Keyboard
            val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)

            val addedName = et.text.toString()
            et.text.clear()

            val progressDialog = ProgressDialog(this@MainActivity)
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            apiInterface?.addName(data(addedName))!!.enqueue(object : Callback<data> {
                override fun onResponse(call: Call<data>, response: Response<data>) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "$addedName added", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<data>, t: Throwable) {
                    progressDialog.dismiss()
                    Toast.makeText(this@MainActivity, "something went wrong!", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    fun getNames(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<ArrayList<peopleItem>?>? = apiInterface!!.getNames()

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        call?.enqueue(object : Callback<ArrayList<peopleItem>?> {
            override fun onResponse(call: Call<ArrayList<peopleItem>?>?, response: Response<ArrayList<peopleItem>?>) {
                progressDialog.dismiss()
                Log.d("TAG", "onResponse")
                var displayResponse = ""
                val resource: ArrayList<peopleItem>? = response.body()
                val peopleItems = resource

                for (people in peopleItems!!) {
                    displayResponse += """${people.name} """
                }
                tv.text = displayResponse
            }

            override fun onFailure(call: Call<ArrayList<peopleItem>?>, t: Throwable?) {
                call.cancel()
                Log.d("TAG", "onFailure")
            }
        })
    }
}