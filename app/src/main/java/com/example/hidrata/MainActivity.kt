package com.example.hidrata


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hidrata.TelaAguaActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtAgua: TextView
    private lateinit var txtPassos: TextView
    private lateinit var txtSono: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtAgua = findViewById(R.id.txtAguaValue)
        txtPassos = findViewById(R.id.txtPassosValue)
        txtSono = findViewById(R.id.txtSonoValue)

        findViewById<View>(R.id.cardAgua).setOnClickListener {
            val i = Intent(this, TelaAguaActivity::class.java)
            startActivityForResult(i, 1)
        }

        findViewById<View>(R.id.cardPassos).setOnClickListener {
            val i = Intent(this, TelaPassosActivity::class.java)
            startActivityForResult(i, 2)
        }

        findViewById<View>(R.id.cardSono).setOnClickListener {
            val i = Intent(this, TelaSonoActivity::class.java)
            startActivityForResult(i, 3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {

                1 -> { // Água
                    val litros = data.getStringExtra("agua") ?: "0"
                    txtAgua.text = "$litros L"
                }

                2 -> { // Passos/KM
                    val km = data.getStringExtra("km") ?: "0"
                    txtPassos.text = "$km km"
                }

                3 -> { // Sono
                    val horas = data.getStringExtra("sono") ?: "0"
                    txtSono.text = "$horas horas"
                }
            }
        }
    }
}
