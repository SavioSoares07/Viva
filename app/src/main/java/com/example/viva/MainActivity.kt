package com.example.viva


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtAgua: TextView
    private lateinit var txtPassos: TextView
    private lateinit var txtSono: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Tela de agua
        findViewById<View>(R.id.cardAgua).setOnClickListener {
            val i = Intent(this, TelaAguaActivity::class.java)
            startActivityForResult(i, 1)
        }
        //Tela de Passos
        findViewById<View>(R.id.cardPassos).setOnClickListener {
            val i = Intent(this, TelaPassosActivity::class.java)
            startActivityForResult(i, 2)
        }
        //Tela de sono
        findViewById<View>(R.id.cardSono).setOnClickListener {
            val i = Intent(this, TelaSonoActivity::class.java)
            startActivityForResult(i, 3)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
